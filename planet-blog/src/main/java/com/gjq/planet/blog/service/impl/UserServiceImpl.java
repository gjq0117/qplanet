package com.gjq.planet.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.crypto.SecureUtil;
import com.gjq.planet.blog.cache.redis.batch.UserSummerInfoCache;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.event.UserInfoModifyEvent;
import com.gjq.planet.blog.event.UserLoginSuccessEvent;
import com.gjq.planet.blog.event.UserLogoutSuccessEvent;
import com.gjq.planet.blog.event.UserRegisterSuccessEvent;
import com.gjq.planet.blog.mapper.UserMapper;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.blog.service.adapter.GroupMemberBuilder;
import com.gjq.planet.blog.service.adapter.UserBuilder;
import com.gjq.planet.common.utils.CursorSplitUtils;
import com.gjq.planet.blog.utils.EmailUtil;
import com.gjq.planet.blog.utils.TokenUtils;
import com.gjq.planet.common.constant.BlogRedisKey;
import com.gjq.planet.common.constant.CommonConstant;
import com.gjq.planet.common.constant.EmailConstant;
import com.gjq.planet.common.constant.MailConstant;
import com.gjq.planet.common.domain.dto.RequestInfo;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import com.gjq.planet.common.domain.vo.req.groupmember.GroupMemberReq;
import com.gjq.planet.common.domain.vo.req.user.*;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.domain.vo.resp.user.LoginResp;
import com.gjq.planet.common.domain.vo.resp.user.UserInfoResp;
import com.gjq.planet.common.domain.vo.resp.user.UserListResp;
import com.gjq.planet.common.domain.vo.resp.user.UserSummerInfoResp;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;
import com.gjq.planet.common.enums.blog.YesOrNoEnum;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.CommonUtil;
import com.gjq.planet.common.utils.RedisUtils;
import com.gjq.planet.common.utils.RequestHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author: gjq0117
 * @date: 2024/4/13 16:18
 * @description: 用户业务
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserSummerInfoCache userSummerInfoCache;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public LoginResp adminLogin(LoginReq req) {
        // 登录校验
        User user = this.loginValid(req);
        // 判断是否是管理员账号
        AssertUtil.isTrue(isAdmin(user.getId()), "您的账号没有管理员权限噢!!!");
        // 改变用户状态（最后上线时间/是否在线的状态/ip信息）
        applicationEventPublisher.publishEvent(new UserLoginSuccessEvent(this, user));
        // 登录成功 生成token
        String token = tokenUtils.createToken(user.getId());
        return LoginResp.builder()
                .userType(user.getUserType())
                .token(token)
                .build();
    }

    @Override
    public LoginResp userLogin(LoginReq req) {
        User user = loginValid(req);
        // 改变用户状态（最后上线时间/是否在线的状态/ip信息）
        applicationEventPublisher.publishEvent(new UserLoginSuccessEvent(this, user));
        // 登录成功 生成token
        String token = tokenUtils.createToken(user.getId());
        return LoginResp.builder()
                .userType(user.getUserType())
                .token(token)
                .build();
    }

    @Override
    public void logout() {
        RequestInfo requestInfo = RequestHolder.get();
        Long uid = requestInfo.getUid();
        String redisUserToken = tokenUtils.getRedisUserToken(uid);
        AssertUtil.isNotEmpty(redisUserToken, "你还没有登录噢!!!");
        tokenUtils.removeRedisUserToken(uid);
        User user = userDao.getById(uid);
        applicationEventPublisher.publishEvent(new UserLogoutSuccessEvent(this, user));
    }

    @Override
    public void logoutByUid(Long uid) {
        tokenUtils.removeRedisUserToken(uid);
        User user = userDao.getById(uid);
        applicationEventPublisher.publishEvent(new UserLogoutSuccessEvent(this, user));
    }


    @Override
    public Boolean isAdmin(Long uid) {
        if (Objects.isNull(uid)) {
            return false;
        }
        User user = userDao.getById(uid);
        if (Objects.nonNull(user)) {
            Integer userType = user.getUserType();
            return userType != null
                    && (SystemRoleEnum.SUPER_ADMIN.getCode().equals(userType) || SystemRoleEnum.ADMIN.getCode().equals(userType));
        }
        return false;
    }

    @Override
    public String getRegisterCode(UserRegisterReq req) {
        AssertUtil.isFalse(isExistUserName(req.getUsername()), "用户名存在啦！再换一个吧~");
        AssertUtil.isFalse(isExistEmail(req.getEmail()), "邮箱已经被注册了噢！再换一个吧~");
        //1、根据注册信息生成一个唯一的key
        String randomKey = UUID.randomUUID().toString().replace("-", "");
        String redisKey = getRegisterCodeKey(req.getUsername(), randomKey);
        //2、随机生成一个数组验证码
        String code = CommonUtil.getRandomCode(EmailConstant.REGISTER_CODE_LENGTH);
        //3、将验证码存放到redis并设置一定的过期时间
        RedisUtils.set(redisKey, code, BlogRedisKey.REGISTER_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        //4、发送邮箱给用户
        emailUtil.sendHtmlMail(req.getEmail(), MailConstant.MAIL_SUBJECT, MailConstant.getRegCodeMailText(code, req.getUsername()));
        return randomKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void register(UserRegisterReq req) {
        AssertUtil.isFalse(isExistUserName(req.getUsername()), "用户名存在啦！再换一个吧~");
        AssertUtil.isFalse(isExistEmail(req.getEmail()), "邮箱已经被注册了噢！再换一个吧~");
        // 验证code
        String redisKey = getRegisterCodeKey(req.getUsername(), req.getKey());
        String redisCode = RedisUtils.getStr(redisKey);
        AssertUtil.isTrue(Objects.nonNull(req.getCode()) && req.getCode().equals(redisCode), "验证码错误！！！");
        User user = UserBuilder.buildFromRegisReq(req);
        userDao.save(user);
        // 删除redis中验证码
        RedisUtils.del(redisKey);
        // 更新IP信息、发放一些物品等（事件发布）
        applicationEventPublisher.publishEvent(new UserRegisterSuccessEvent(this, user));
    }

    @Override
    public UserInfoResp getUserInfo() {
        Long uid = RequestHolder.get().getUid();
        User user = userDao.getById(uid);
        if (Objects.isNull(user)) {
            return null;
        }
        return UserBuilder.buildUserInfo(user);
    }

    @Override
    public String getModifyPwdCode(ModifyPwdReq req) {
        User user = userDao.getUserByEmail(req.getEmail());
        AssertUtil.isNotEmpty(user, "邮箱还没有被注册噢~~~");
        String randomKey = UUID.randomUUID().toString().replace("-", "");
        String redisKey = getModifyPwdCodeKey(req.getEmail(), randomKey);
        String code = CommonUtil.getRandomCode(EmailConstant.REGISTER_CODE_LENGTH);
        RedisUtils.set(redisKey, code, BlogRedisKey.MODIFY_PWD_EXPIRE_MINUTES, TimeUnit.MINUTES);
        // 发邮箱
        emailUtil.sendHtmlMail(req.getEmail(), MailConstant.MAIL_SUBJECT, MailConstant.getModifyPwdCodeMailText(code, user.getNickname()));
        return randomKey;
    }

    @Override
    public void modifyPwd(ModifyPwdReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        User user = userDao.getUserByEmail(req.getEmail());
        AssertUtil.isNotEmpty(user, "邮箱还没有被注册噢~~~");
        // 验证code
        String redisKey = getModifyPwdCodeKey(req.getEmail(), req.getKey());
        String redisCode = RedisUtils.getStr(redisKey);
        AssertUtil.isTrue(Objects.nonNull(req.getCode()) && req.getCode().equals(redisCode), "验证码错误！！！");
        // 修改密码
        User update = UserBuilder.buildModifyPwd(req);
        userDao.modifyPwdByEmail(update);
        // 删除redis中的验证码
        RedisUtils.del(redisKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void modifyUserInfo(ModifyUserInfoReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        validModifyInfo(req);
        User update = UserBuilder.buildFromModifyReq(req);
        userDao.updateById(update);
        // 发布用户修改事件
        applicationEventPublisher.publishEvent(new UserInfoModifyEvent(this ,update.getId()));
    }

    /**
     *  校验修改信息
     *
     * @param req
     */
    private void validModifyInfo(ModifyUserInfoReq req) {
        //用户名不能重复
        User user = userDao.getByUserName(req.getUsername());
        AssertUtil.isTrue(Objects.isNull(user) || user.getId().equals(RequestHolder.get().getUid()), "用户名称已经被占用了噢~ 再换一个吧！");
        // 手机号不能相同
        user = userDao.getByPhone(req.getPhone());
        AssertUtil.isTrue(Objects.isNull(user) || user.getId().equals(RequestHolder.get().getUid()), "手机号已经被注册了噢~ 再换一个吧！");
        // 邮箱不能重复
        // 用户昵称不能相同
        user = userDao.getByNickName(req.getNickname());
        AssertUtil.isTrue(Objects.isNull(user) || user.getId().equals(RequestHolder.get().getUid()), "用户昵称已经被占用了噢~ 再换一个吧！");
    }

    @Override
    public List<UserListResp> getUserList(UserListReq req) {
        List<UserListResp> result = null;
        if (!userListReqIsEmpty(req)) {
            // 有条件查询（直接查库）
            List<User> userList = userMapper.searchUserList(req);
            result = UserBuilder.buildListResp(userList);
            return result;
        }
        // 查数据库
        List<User> userList = userDao.list();
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            result = UserBuilder.buildListResp(userList);
        }
        return result;
    }

    @Override
    public void changeUserStatus(Long uid, Integer status) {
        userDao.changeUserStatus(uid, status);
        if (YesOrNoEnum.NO.getCode().equals(status)) {
            // 如果是改成冻结状态 则强制让用户下线
            logoutByUid(uid);
        }
    }

    @Override
    public CursorPageBaseResp<GroupMemberResp> getUserCursorPage(List<Long> uidList, GroupMemberReq req) {
        // 先查询出在线的，再查询出不在线的
        Pair<UserActiveStatusEnum, String> cursorPair = CursorSplitUtils.getActiveStatusPair(req.getCursor());
        UserActiveStatusEnum activeStatusEnum = cursorPair.getKey();
        String cursor = cursorPair.getValue();
        Boolean isLast = Boolean.FALSE;
        List<GroupMemberResp> result = new ArrayList<>();
        // 在线的
        if (activeStatusEnum.equals(UserActiveStatusEnum.ONLINE)) {
            CursorPageBaseResp<User> userCursorPageResp = userDao.listUserPage(uidList, new CursorPageBaseReq(req.getPageSize(), cursor), activeStatusEnum);
            result.addAll(GroupMemberBuilder.buildGroupMember(userCursorPageResp.getList()));
            // 如果是最后一条且不足pageSize的，需要去离线列表中补齐
            if (userCursorPageResp.getIsLast() && userCursorPageResp.getList().size() < uidList.size()) {
                activeStatusEnum = UserActiveStatusEnum.OFFLINE;
                userCursorPageResp = userDao.listUserPage(uidList, new CursorPageBaseReq(req.getPageSize() - userCursorPageResp.getList().size(), cursor), activeStatusEnum);
                result.addAll(GroupMemberBuilder.buildGroupMember(userCursorPageResp.getList()));
            }
            cursor = userCursorPageResp.getCursor();
            isLast = userCursorPageResp.getIsLast();
        } else if (activeStatusEnum.equals(UserActiveStatusEnum.OFFLINE)) {
            // 离线的
            CursorPageBaseResp<User> userCursorPageResp = userDao.listUserPage(uidList, new CursorPageBaseReq(req.getPageSize(), cursor), activeStatusEnum);
            result.addAll(GroupMemberBuilder.buildGroupMember(userCursorPageResp.getList()));
            cursor = userCursorPageResp.getCursor();
            isLast = userCursorPageResp.getIsLast();
        }
        return new CursorPageBaseResp<>(CursorSplitUtils.generateActiveStatusCursor(activeStatusEnum, cursor), isLast, result);
    }

    @Override
    public List<UserSummerInfoResp> batchRefreshUserSummerInfo(RefreshUserSummerReq refreshUserSummerReq) {
        if (CollectionUtil.isEmpty(refreshUserSummerReq.getReqList())) {
            return null;
        }
        List<Long> uidList = new ArrayList<>();
        List<Long> lastRefreshDateList = new ArrayList<>();
        for (RefreshUserSummerReq.InfoReq req : refreshUserSummerReq.getReqList()) {
            uidList.add(req.getUid());
            lastRefreshDateList.add(req.getLastRefreshTime());
        }
        List<UserSummerInfoResp> batch = userSummerInfoCache.getBatch(uidList);
        // 过滤掉不需要刷新的（lastRefreshTime < lastUpdateTime）
        return batch.stream().filter(userSummerInfoResp -> {
            Long lastUpdateTime = userSummerInfoResp.getLastUpdateTime();
            Long lastRefreshDate = lastRefreshDateList.get(uidList.indexOf(userSummerInfoResp.getUid()));
            return Objects.isNull(lastRefreshDate) || lastRefreshDate < lastUpdateTime;
        }).collect(Collectors.toList());
    }

    /**
     * 判断用户列表查询请求的请求体是否为空
     *
     * @param req
     * @return
     */
    private boolean userListReqIsEmpty(UserListReq req) {
        if (Objects.isNull(req)) {
            return true;
        }
        return Objects.isNull(req.getUserType()) &&
                Objects.isNull(req.getIsActive()) &&
                Objects.isNull(req.getUserStatus()) &&
                Objects.isNull(req.getGender()) &&
                (Objects.isNull(req.getPhone()) || StringUtils.isBlank(req.getPhone())) &&
                (Objects.isNull(req.getEmail()) || StringUtils.isBlank(req.getEmail())) &&
                (Objects.isNull(req.getUsername()) || StringUtils.isBlank(req.getUsername()));

    }

    /**
     * 登录校验
     *
     * @param req 登录请求实体
     * @return
     */
    private User loginValid(LoginReq req) {
        User user = userDao.getByAccount(req.getAccount());
        // 1、判断账号是否存在
        AssertUtil.isNotEmpty(user, "用户不存在!!!快去联系站长吧");
        // 2、判断密码是否正确
        AssertUtil.isTrue(validPassword(req.getPassword(), user), "输入的密码错误噢!!!请重新输入");
        // 3、判断账号是否被冻结
        AssertUtil.isTrue(YesOrNoEnum.YES.getCode().equals(user.getUserStatus()), "账号已被冻结!!!请联系管理员");
        return user;
    }

    /**
     * 获取修改密码验证码的redisKey
     *
     * @param email
     * @param randomKey
     * @return
     */
    private String getModifyPwdCodeKey(String email, String randomKey) {
        return BlogRedisKey.getKey(BlogRedisKey.MODIFY_PWD_CODE, email + randomKey);
    }


    /**
     * 获取注册验证码的redisKey
     *
     * @param username
     * @return
     */
    private String getRegisterCodeKey(String username, String randomKey) {
        return BlogRedisKey.getKey(BlogRedisKey.REGISTER_CODE, username + randomKey);
    }

    /**
     * 判断邮箱是否被使用
     *
     * @param email
     * @return
     */
    private Boolean isExistEmail(String email) {
        return userDao.isExistEmail(email);
    }

    /**
     * 判断用户名是否存在
     *
     * @param userName
     * @return
     */
    private Boolean isExistUserName(String userName) {
        return userDao.isExistUserName(userName);
    }

    /**
     * 验证密码
     *
     * @param password AES加密密码
     * @param user
     * @return
     */
    private boolean validPassword(String password, User user) {
        String parsePwd = new String(SecureUtil.aes(CommonConstant.PWD_AES.getBytes(StandardCharsets.UTF_8)).decrypt(password));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(parsePwd, user.getPassword());
    }

}
