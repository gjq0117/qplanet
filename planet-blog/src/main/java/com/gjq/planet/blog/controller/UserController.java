package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.annotation.NotToken;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.common.domain.vo.req.user.AdminLoginReq;
import com.gjq.planet.blog.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理", tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    @NotToken
    @ApiOperation("管理员登录")
    @PostMapping("/adminLogin")
    public ApiResult<String> adminLogin(@RequestBody @Valid AdminLoginReq req) {
        String token = userService.adminLogin(req);
        return ApiResult.success(token);
    }

    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public ApiResult<Void> logout() {
        userService.logout();
        return ApiResult.success();
    }
}

