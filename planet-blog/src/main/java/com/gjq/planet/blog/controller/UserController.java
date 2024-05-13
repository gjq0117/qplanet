package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.annotation.NotToken;
import com.gjq.planet.blog.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.user.*;
import com.gjq.planet.common.domain.vo.resp.user.LoginResp;
import com.gjq.planet.common.domain.vo.resp.user.UserInfoResp;
import com.gjq.planet.common.domain.vo.resp.user.UserListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ApiResult<LoginResp> adminLogin(@RequestBody @Valid LoginReq req) {
        return ApiResult.success(userService.adminLogin(req));
    }

    @NotToken
    @ApiOperation("用户登录")
    @PostMapping("/userLogin")
    public ApiResult<LoginResp> userLogin(@RequestBody @Valid LoginReq req) {
        return ApiResult.success(userService.userLogin(req));
    }

    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public ApiResult<Void> logout() {
        userService.logout();
        return ApiResult.success();
    }

    @NotToken
    @PostMapping("/getRegisterCode")
    @ApiOperation("获取用户注册验证码")
    public ApiResult<String> getRegisterCode(@RequestBody @Valid UserRegisterReq userRegisterReq) {
        return ApiResult.success(userService.getRegisterCode(userRegisterReq));
    }

    @NotToken
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ApiResult<Void> register(@RequestBody @Valid UserRegisterReq req) {
        userService.register(req);
        return ApiResult.success();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getUserInfo")
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo());
    }

    @NotToken
    @ApiOperation("获取修改密码时的验证码")
    @PostMapping("/getModifyPwdCode")
    public ApiResult<String> getModifyPwdCode(@RequestBody @Valid ModifyPwdReq req) {
        return ApiResult.success(userService.getModifyPwdCode(req));
    }

    @NotToken
    @ApiOperation("修改密码")
    @PutMapping("/modifyPwd")
    public ApiResult<Void> modifyPwd(@RequestBody @Valid ModifyPwdReq req) {
        userService.modifyPwd(req);
        return ApiResult.success();
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/modifyUserInfo")
    public ApiResult<Void> modifyUserInfo(@RequestBody @Valid ModifyUserInfoReq req) {
        userService.modifyUserInfo(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @ApiOperation("获取用户列表")
    @PostMapping("/getUserList")
    public ApiResult<List<UserListResp>> getUserList(@RequestBody(required = false) @Valid UserListReq req) {
        return ApiResult.success(userService.getUserList(req));
    }

    @PlanetAdmin
    @PutMapping("/changeUserStatus")
    @ApiOperation("改变用户状态")
    public ApiResult<Void> changeUserStatus(@RequestParam("uid") Long uid, @RequestParam("status") Integer status) {
        userService.changeUserStatus(uid, status);
        return ApiResult.success();
    }

    @PlanetAdmin
    @PutMapping("/logoutByUid/{uid}")
    @ApiOperation("强制下线指定uid的用户")
    public ApiResult<Void> logoutByUid(@PathVariable("uid") Long uid) {
        userService.logoutByUid(uid);
        return ApiResult.success();
    }
}

