package com.gjq.planet.blog.service;


import com.gjq.planet.common.domain.vo.req.user.AdminLoginReq;

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
     *  管理员登录
     *
     * @param req
     * @return
     */
    String adminLogin(AdminLoginReq req);

    /**
     *  用户退出
     */
    void logout();
}
