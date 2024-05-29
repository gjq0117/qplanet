package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IUserApplyService;
import com.gjq.planet.common.domain.vo.req.userapply.UserApplyPageReq;
import com.gjq.planet.common.domain.vo.req.userapply.FriendApplyReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.userapply.FriendApplyResp;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户申请表 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/userApply")
@Api(tags = "用户申请接口")
public class UserApplyController {

    @Autowired
    private IUserApplyService userApplyService;

    @PostMapping("/friendApply")
    @ApiOperation("好友申请")
    public ApiResult<Void> friendApply(@RequestBody @Valid FriendApplyReq req) {
        userApplyService.friendApply(req);
        return ApiResult.success();
    }

    @PostMapping("/getFriendApplyPage")
    @ApiOperation("获取好友申请列表")
    public ApiResult<CursorPageBaseResp<FriendApplyResp>> getFriendApplyPage(@RequestBody UserApplyPageReq req) {
        return ApiResult.success(userApplyService.pageFriendApplyList(req));
    }

    @ApiOperation("同意好友申请")
    @GetMapping("/agreeFriendApply/{uid}")
    public ApiResult<Void> agreeFriendApply(@PathVariable("uid") Long uid) {
        Long currUid = RequestHolder.get().getUid();
        userApplyService.agreeFriendApply(currUid, uid);
        return ApiResult.success();
    }

    @ApiOperation("拒绝好友申请")
    @GetMapping("/rejectFriendApply/{uid}")
    public ApiResult<Void> rejectFriendApply(@PathVariable("uid") Long uid) {
        Long currUid = RequestHolder.get().getUid();
        userApplyService.rejectFriendApply(currUid, uid);
        return ApiResult.success();
    }


}

