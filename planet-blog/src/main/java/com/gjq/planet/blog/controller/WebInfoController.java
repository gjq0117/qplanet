package com.gjq.planet.blog.controller;


import com.gjq.planet.common.annotation.NotToken;
import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.IWebInfoService;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebStatisticsInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
    @GetMapping("/getWebInfo")
    public ApiResult<WebInfoResp> getWebInfo() {
        return ApiResult.success(webInfoService.getWebInfo());
    }

    @PlanetAdmin
    @ApiOperation("更新网站信息")
    @PutMapping("/updateOrSave")
    public ApiResult<Void> updateOrSave(@RequestBody @Valid WebInfoUpdateReq req) {
        webInfoService.updateOrSave(req);
        return ApiResult.success();
    }

    @NotToken
    @ApiOperation("获取网站统计信息")
    @GetMapping("/getWebStatisticsInfo")
    public ApiResult<WebStatisticsInfo> getWebStatisticsInfo() {
        return ApiResult.success(webInfoService.getWebStatisticsInfo());
    }
}

