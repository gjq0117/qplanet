package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.annotation.NotToken;
import com.gjq.planet.blog.service.IWebInfoService;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 网站信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/webInfo")
@Api(tags = "网站信息接口")
public class WebInfoController {

    @Autowired
    private IWebInfoService webInfoService;

    @NotToken
    @ApiOperation("获取网站基本信息")
    @GetMapping("/getOne")
    public ApiResult<WebInfoResp> getWebInfo() {
        return ApiResult.success(webInfoService.getWebInfo());
    }

    @ApiOperation("更新网站信息")
    @PutMapping("/update")
    public ApiResult<Void> update(@RequestBody WebInfoUpdateReq req) {
        webInfoService.updateById(req);
        return ApiResult.success();
    }
}
