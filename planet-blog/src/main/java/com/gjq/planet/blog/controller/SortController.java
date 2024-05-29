package com.gjq.planet.blog.controller;


import com.gjq.planet.common.annotation.NotToken;
import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.ISortService;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.sort.SortReq;
import com.gjq.planet.common.domain.vo.resp.sort.HasArticleSortResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortPageResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/sort")
@Api(tags = "分类管理")
public class SortController {

    @Autowired
    private ISortService sortService;

    @PlanetAdmin
    @PostMapping("/insertOne")
    @ApiOperation("新增分类")
    public ApiResult<Void> insertOne(@RequestBody @Valid SortReq req) {
        sortService.insertOne(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @ApiOperation("获取所有的分类信息")
    @GetMapping("/list")
    public ApiResult<List<SortResp>> list() {
        return ApiResult.success(sortService.list());
    }

    @ApiOperation("通过id删除分类信息")
    @DeleteMapping("/deleteById/{sortId}")
    public ApiResult<Void> deleteById(@PathVariable("sortId") Long sortId) {
        sortService.deleteById(sortId);
        return ApiResult.success();
    }

    @PlanetAdmin
    @ApiOperation("通过id更新分类信息")
    @PutMapping("/update")
    public ApiResult<Void> update(@RequestBody @Valid SortReq req) {
        sortService.updateById(req);
        return ApiResult.success();
    }


    @ApiOperation(("通过key模糊查询分类信息"))
    @GetMapping("/searchByKey")
    public ApiResult<List<SortResp>> searchByKey(@RequestParam String searchKey) {
        return ApiResult.success(sortService.searchByKey(searchKey));
    }

    @NotToken
    @ApiOperation(("获取有文章的分类信息以及该分类下最新的六篇文章"))
    @GetMapping("/getSortAndNewArticle")
    public ApiResult<List<HasArticleSortResp>> getSortAndNewArticle() {
        return ApiResult.success(sortService.getSortAndNewArticleForSix());
    }

    @NotToken
    @GetMapping("/getSortPageResp")
    @ApiOperation("通过sortId以及labelId查询对应的文章列表")
    public ApiResult<SortPageResp> getSortPageResp(@RequestParam("sortId") @ApiParam("分类ID") Long sortId,
                                                   @RequestParam(value = "labelId", required = false) @ApiParam("标签Id") Long labelId) {
        return ApiResult.success(sortService.getSortPageResp(sortId, labelId));
    }
}

