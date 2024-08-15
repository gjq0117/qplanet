package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IGroupMemberService;
import com.gjq.planet.common.domain.vo.req.groupmember.GroupMemberReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 群成员信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@RestController
@RequestMapping("/groupMember")
@Api(tags = "群成员信息")
public class GroupMemberController {

    @Autowired
    private IGroupMemberService groupMemberService;

    @GetMapping("/getGroupMemberPage")
    @ApiOperation("获取群成员分页信息")
    public ApiResult<CursorPageBaseResp<GroupMemberResp>> getGroupMemberPage(@Valid GroupMemberReq req) {
        return ApiResult.success(groupMemberService.getGroupMemberPage(req));
    }

    @ApiOperation("获取群组用户@列表")
    @GetMapping("/getGroupMemberAtPage")
    public ApiResult<CursorPageBaseResp<Long>> getUserAtList(@Valid GroupMemberReq atPageReq) {
        return ApiResult.success(groupMemberService.getGroupMemberAtPage(atPageReq));
    }
}

