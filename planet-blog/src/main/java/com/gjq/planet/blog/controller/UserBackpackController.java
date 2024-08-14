package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IUserBackpackService;
import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.domain.vo.req.userbackpack.ImgEmojiPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>
 * 用户背包表 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
@RestController
@RequestMapping("/userBackpack")
@Api(tags = "用户背包接口")
public class UserBackpackController {

    @Autowired
    private IUserBackpackService userBackpackService;

    @ApiOperation("通过指定uid获取表情包分页列表")
    @PostMapping("/getImgEmojiPageByUid")
    public ApiResult<CursorPageBaseResp<ItemConfig>> getImgEmojiPageByUid(@RequestBody @Valid ImgEmojiPageReq req) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(userBackpackService.getImgEmojiPage(req, uid));
    }

    @PostMapping("/addImgEmoji")
    @ApiOperation("添加表情包")
    public ApiResult<Void> addImgEmoji(String imgUrl) {
        Long uid = RequestHolder.get().getUid();
        userBackpackService.addImgEmoji(uid, imgUrl);
        return ApiResult.success();
    }
}

