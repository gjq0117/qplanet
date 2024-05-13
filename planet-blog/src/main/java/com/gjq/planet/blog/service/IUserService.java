package com.gjq.planet.blog.service;


import com.gjq.planet.common.domain.vo.req.user.*;
import com.gjq.planet.common.domain.vo.resp.user.LoginResp;
import com.gjq.planet.common.domain.vo.resp.user.UserInfoResp;
import com.gjq.planet.common.domain.vo.resp.user.UserListResp;

import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
public interface IUserService {

    /**
     * 管理员登录
     *
     * @param req
     * @return
     */
    LoginResp adminLogin(LoginReq req);


    /**
     * 普通用户登录
     *
     * @param req
     * @return
     */
    LoginResp userLogin(LoginReq req);

    /**
     * 用户退出
     */
    void logout();

    /**
     *  下线指定uid的用户(用户管理员操作)
     */
    void logoutByUid(Long uid);

    /**
     * 判断用户是否是管理员
     *
     * @param uid
     * @return
     */
    Boolean isAdmin(Long uid);

    /**
     * 获取注册验证码
     *
     * @param userRegisterReq
     * @return
     */
    String getRegisterCode(UserRegisterReq userRegisterReq);

    /**
     * 用户注册
     *
     * @param req
     */
    void register(UserRegisterReq req);

    /**
     * 获取用户信息
     *
     * @return
     */
    UserInfoResp getUserInfo();

    /**
     * 获取在修改密码时请求的验证码
     *
     * @param req
     * @return
     */
    String getModifyPwdCode(ModifyPwdReq req);

    /**
     * 修改密码
     *
     * @param req
     */
    void modifyPwd(ModifyPwdReq req);

    /**
     * 修改用户信息
     *
     * @param req
     */
    void modifyUserInfo(ModifyUserInfoReq req);

    /**
     * 根据条件模糊查询用户列表
     *
     * @param req
     * @return
     */
    List<UserListResp> getUserList(UserListReq req);

    /**
     *  改变用户状态
     *
     * @param uid
     * @param status
     */
    void changeUserStatus(Long uid, Integer status);
}
