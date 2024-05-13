package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.blog.utils.CommonUtil;
import com.gjq.planet.blog.utils.RequestHolder;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.user.ModifyPwdReq;
import com.gjq.planet.common.domain.vo.req.user.ModifyUserInfoReq;
import com.gjq.planet.common.domain.vo.req.user.UserRegisterReq;
import com.gjq.planet.common.domain.vo.resp.user.UserInfoResp;
import com.gjq.planet.common.domain.vo.resp.user.UserListResp;
import com.gjq.planet.common.enums.GenderEnum;
import com.gjq.planet.common.enums.SystemRoleEnum;
import com.gjq.planet.common.enums.YesOrNoEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
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
        String nickname = buildNickName();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .username(registerReq.getUsername())
                .password(bCryptPasswordEncoder.encode(registerReq.getPassword()))
                .email(registerReq.getEmail())
                // 默认nickname
                .nickname(nickname)
                // 默认性别位置
                .gender(GenderEnum.UNKNOWN.getType())
                .userStatus(YesOrNoEnum.YES.getCode())
                .userType(SystemRoleEnum.CUSTOMER.getCode())
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
        return userInfoResp;
    }

    /**
     *  构建修改密码实体
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
     *  通过修改用户信息请求实体构建用户信息
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
     *  构建用户列表
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
}
