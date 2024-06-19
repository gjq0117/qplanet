package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 会话信息表 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@RestController
@RequestMapping("/contact")
@Api(tags = "会话列表信息")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @PostMapping("/getContactListPage")
    @ApiOperation("获取会话分页列表")
    public ApiResult<CursorPageBaseResp<ContactResp>> getContactListPage(@RequestBody @Valid ContactPageReq req) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(contactService.getContactListPage(uid, req));
    }

    @GetMapping("/getContactResp/{roomId}")
    @ApiOperation("主动拉取会话信息")
    public ApiResult<ContactResp> getContactResp(@PathVariable("roomId") Long roomId) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(contactService.getContactResp(uid, roomId));
    }
}

