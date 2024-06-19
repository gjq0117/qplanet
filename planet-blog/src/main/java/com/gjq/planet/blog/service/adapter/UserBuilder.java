package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.IpInfo;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.user.ModifyPwdReq;
import com.gjq.planet.common.domain.vo.req.user.ModifyUserInfoReq;
import com.gjq.planet.common.domain.vo.req.user.UserRegisterReq;
import com.gjq.planet.common.domain.vo.resp.user.UserInfoResp;
import com.gjq.planet.common.domain.vo.resp.user.UserListResp;
import com.gjq.planet.common.domain.vo.resp.user.UserSummerInfoResp;
import com.gjq.planet.common.enums.blog.GenderEnum;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.enums.blog.YesOrNoEnum;
import com.gjq.planet.common.utils.CommonUtil;
import com.gjq.planet.common.utils.RequestHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/28 21:06
 * @description: 用户信息构造器
 */
public class UserBuilder {

    /**
     * 通过注册信息构建用户信息
     *
     * @param registerReq
     * @return
     */
    public static User buildFromRegisReq(UserRegisterReq registerReq) {
        // 随机构建nickname
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .username(registerReq.getUsername())
                .password(bCryptPasswordEncoder.encode(registerReq.getPassword()))
                .email(registerReq.getEmail())
                // 默认nickname
                .nickname(buildNickName())
                // 默认性别未知
                .gender(GenderEnum.UNKNOWN.getType())
                // 默认头像
                .avatar("https://minio.qplanet.cn/planet/blog/2024-06-06/10000/3ea6beec64369c2642b92c6726f1epng.png")
                .userStatus(YesOrNoEnum.YES.getCode())
                .userType(SystemRoleEnum.CUSTOMER.getCode())
                .lastActiveTime(new Date())
                .build();
    }

    private static String buildNickName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("用户");
        stringBuilder.append(CommonUtil.getRandomCode(6));
        return stringBuilder.toString();
    }

    /**
     * 构建用户信息返回实体
     *
     * @param user
     * @return
     */
    public static UserInfoResp buildUserInfo(User user) {
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtils.copyProperties(user, userInfoResp);
        userInfoResp.setUid(user.getId());
        return userInfoResp;
    }

    /**
     * 构建修改密码实体
     *
     * @param req
     * @return
     */
    public static User buildModifyPwd(ModifyPwdReq req) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(req.getEmail())
                .password(bCryptPasswordEncoder.encode(req.getNewPwd()))
                .build();
    }

    /**
     * 通过修改用户信息请求实体构建用户信息
     *
     * @param req
     * @return
     */
    public static User buildFromModifyReq(ModifyUserInfoReq req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        user.setId(RequestHolder.get().getUid());
        return user;
    }

    /**
     * 构建用户列表
     *
     * @param userList
     * @return
     */
    public static List<UserListResp> buildListResp(List<User> userList) {
        return userList.stream().map(user -> {
            UserListResp userListResp = new UserListResp();
            BeanUtils.copyProperties(user, userListResp);
            return userListResp;
        }).collect(Collectors.toList());
    }

    /**
     * 构建用户聚合信息
     *
     * @param user 用户信息
     * @return 用户聚合列表
     */
    public static UserSummerInfoResp buildUserSummerInfo(User user) {
        UserSummerInfoResp userSummerInfoResp = new UserSummerInfoResp();
        BeanUtils.copyProperties(user, userSummerInfoResp);
        userSummerInfoResp.setUid(user.getId());
        // 构建地区信息
        IpInfo ipInfo = user.getIpInfo();
        if (Objects.nonNull(ipInfo)) {
            userSummerInfoResp.setLocPlace(ipInfo.getUpdateIpDetail().getCity());
        } else {
            userSummerInfoResp.setLocPlace("未知");
        }
        // 刷新时间
        userSummerInfoResp.setLastUpdateTime(new Date().getTime());
        return userSummerInfoResp;
    }
}
