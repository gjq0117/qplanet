package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.ILabelService;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.label.LabelReq;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 标签信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/label")
@Api(tags = "标签信息")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @PlanetAdmin
    @PostMapping("/insertOne")
    @ApiOperation("新增一条标签信息")
    public ApiResult<Void> insertOne(@RequestBody @Valid LabelReq req) {
        labelService.insertOne(req);
        return ApiResult.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询标签列表")
    public ApiResult<List<LabelResp>> list() {
        return ApiResult.success(labelService.list());
    }

    @PlanetAdmin
    @PutMapping("/update")
    @ApiOperation("更新标签信息")
    public ApiResult<Void> update(@RequestBody @Valid LabelReq req) {
        labelService.updateById(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @DeleteMapping("/deleteById/{labelId}")
    @ApiOperation(("通过id删除标签信息"))
    public ApiResult<Void> deleteById(@PathVariable("labelId") Long labelId) {
        labelService.deleteById(labelId);
        return ApiResult.success();
    }

    @GetMapping("/searchByKey")
    @ApiOperation("通过关键词模糊查询标签信息")
    public ApiResult<List<LabelResp>> searchByKey(@RequestParam("searchKey") String searchKey) {
        return ApiResult.success(labelService.searchByKey(searchKey));
    }

    @GetMapping("/getById/{labelId}")
    @ApiOperation("通过id获取标签信息")
    public ApiResult<LabelResp> getById(@PathVariable("labelId") Long labelId) {
        return ApiResult.success(labelService.getById(labelId));
    }

    @GetMapping("/getListBySortId/{sortId}")
    public ApiResult<List<LabelResp>> getListBySortId(@PathVariable("sortId") Long sortId) {
        return ApiResult.success(labelService.getListBySortId(sortId));
    }
}

