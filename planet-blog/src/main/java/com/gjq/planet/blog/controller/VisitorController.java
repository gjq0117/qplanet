package com.gjq.planet.blog.controller;


import com.gjq.planet.common.annotation.NotToken;
import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.IVisitorService;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.visitor.VisitorListReq;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorListResp;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorProvinceResp;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 网站访问信息 前端控制器
 * 匿名访问用户：一个IP一天记一次
 * 登录用户： ip + uid 一天记一次
 * </p>
 *
 * @author gjq
 * @since 2024-04-30
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private IVisitorService visitorService;

    @NotToken
    @ApiOperation("保存网站访问信息")
    @GetMapping("/saveWebVisitInfo")
    public ApiResult<Void> saveWebVisitInfo() {
        visitorService.visitWeb();
        return ApiResult.success();
    }

    @PlanetAdmin
    @ApiOperation("获取省份信息")
    @GetMapping("/getProvinceList")
    private ApiResult<List<VisitorProvinceResp>> getProvinceList() {
        return ApiResult.success(visitorService.getProvinceList());
    }

    @PlanetAdmin
    @ApiOperation("查询访问信息列表")
    @PostMapping("/getVisitorList")
    public ApiResult<List<VisitorListResp>> getVisitorList(@RequestBody(required = false) VisitorListReq req) {
        return ApiResult.success(visitorService.getVisitorList(req));
    }
}

