package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IUserFriendService;
import com.gjq.planet.common.domain.vo.req.userfriend.PutFriendRemarkReq;
import com.gjq.planet.common.domain.vo.req.userfriend.UserFriendPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.domain.vo.resp.userfriend.FriendDetailedResp;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * <p>
 * 用户好友表 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/userFriend")
@Api(tags = "好友接口")
public class UserFriendController {

    @Autowired
    private IUserFriendService userFriendService;

    @GetMapping("/isFriend/{uid}")
    @ApiOperation("判断指定uid的用户是否是当前用户的好友")
    public ApiResult<Boolean> isFriend(@PathVariable("uid") Long uid) {
        Long currUid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.isFriend(currUid, uid));
    }

    @PostMapping("/pageFriendInfo")
    @ApiOperation("获取好友列表分页信息")
    public ApiResult<CursorPageBaseResp<GroupMemberResp>> pageFriendInfo(@RequestBody @Valid UserFriendPageReq req) {
        return ApiResult.success(userFriendService.pageFriendInfo(req));
    }

    @PostMapping("/pageCareFriendInfo")
    @ApiOperation("获取特别关心好友分页信息")
    public ApiResult<CursorPageBaseResp<GroupMemberResp>> pageCareFriendInfo(@RequestBody @Valid UserFriendPageReq req) {
        return ApiResult.success(userFriendService.pageCareFriendInfo(req));
    }

    @ApiOperation("获取好友详情信息")
    @GetMapping("/getFriendDetailed/{friendUid}")
    public ApiResult<FriendDetailedResp> getFriendDetailed(@PathVariable("friendUid") Long friendUid) {
        Long currUid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.getFriendDetailed(currUid, friendUid));
    }

    @ApiOperation("设置好友备注")
    @PutMapping("/putFriendRemark")
    public ApiResult<Void> putFriendRemark(@RequestBody @Valid PutFriendRemarkReq req) {
        userFriendService.putFriendRemark(RequestHolder.get().getUid(),req);
        return ApiResult.success();
    }

    @ApiOperation("将好友设置为特别关心")
    @PutMapping("/putFriendToCare/{friendUid}")
    public ApiResult<Void> putFriendToCare(@PathVariable("friendUid") Long friendUid) {
        userFriendService.putFriendToCare(RequestHolder.get().getUid(), friendUid);
        return ApiResult.success();
    }

    @ApiOperation("取消好友特别关心")
    @PutMapping("/cancelFriendCare/{friendUid}")
    public ApiResult<Void> cancelFriendCare(@PathVariable("friendUid") Long friendUid) {
        userFriendService.cancelFriendCare(RequestHolder.get().getUid(), friendUid);
        return ApiResult.success();
    }

    @ApiOperation("删除好友")
    @DeleteMapping("/deleteFriend/{friendUid}")
    public ApiResult<Void> deleteFriend(@PathVariable("friendUid") Long friendUid) {
        userFriendService.deleteFriend(RequestHolder.get().getUid(), friendUid);
        return ApiResult.success();
    }

}

