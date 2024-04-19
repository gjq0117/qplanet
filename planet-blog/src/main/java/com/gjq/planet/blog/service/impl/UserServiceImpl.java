package com.gjq.planet.blog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.gjq.planet.blog.constants.CommonConstants;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.enums.SystemRoleEnum;
import com.gjq.planet.blog.enums.YesOrNoEnum;
import com.gjq.planet.blog.event.UserLoginSuccessEvent;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.blog.utils.AssertUtil;
import com.gjq.planet.blog.utils.RequestHolder;
import com.gjq.planet.blog.utils.TokenUtils;
import com.gjq.planet.common.domain.dto.RequestInfo;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.user.AdminLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;


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
    private TokenUtils tokenUtils;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String adminLogin(AdminLoginReq req) {
        User user = userDao.getByAccount(req.getAccount());
        // 1、判断账号是否存在
        AssertUtil.isNotEmpty(user, "用户不存在!!!快去联系站长吧");
        // 2、判断密码是否正确
        AssertUtil.isTrue(validPassword(req.getPassword(), user), "输入的密码错误噢!!!请重新输入");
        // 3、判断账号是否被冻结
        AssertUtil.isTrue(YesOrNoEnum.YES.getCode().equals(user.getUserStatus()), "账号已被冻结!!!请联系管理员");
        // 4、判断是否是管理员账号
        AssertUtil.isTrue(validRoleIsAdmin(user), "您的账号没有管理员权限噢!!!");
        // 5、改变用户状态（最后上线时间/是否在线的状态/ip信息）
        applicationEventPublisher.publishEvent(new UserLoginSuccessEvent(this, user));
        // 6、登录成功 生成token
        return tokenUtils.createToken(user.getId());
    }

    @Override
    public void logout() {
        RequestInfo requestInfo = RequestHolder.get();
        Long uid = requestInfo.getUid();
        String redisUserToken = tokenUtils.getRedisUserToken(uid);
        AssertUtil.isNotEmpty(redisUserToken, "你还没有登录噢!!!");
        tokenUtils.removeRedisUserToken(uid);
    }


    /**
     * 验证是否是管理员
     *
     * @param user
     * @return
     */
    private boolean validRoleIsAdmin(User user) {
        return user.getUserType() != null && (SystemRoleEnum.SUPER_ADMIN.getCode().equals(user.getUserType()) || SystemRoleEnum.ADMIN.getCode().equals(user.getUserType()));
    }

    /**
     * 验证密码
     *
     * @param password AES加密密码
     * @param user
     * @return
     */
    private boolean validPassword(String password, User user) {
        String parsePwd = new String(SecureUtil.aes(CommonConstants.PWD_AES.getBytes(StandardCharsets.UTF_8)).decrypt(password));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(parsePwd, user.getPassword());
    }

}
