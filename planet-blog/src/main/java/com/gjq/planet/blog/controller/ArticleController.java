package com.gjq.planet.blog.controller;


import com.gjq.planet.common.annotation.NotToken;
import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.IArticleService;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.domain.vo.req.article.ArticleReq;
import com.gjq.planet.common.domain.vo.req.article.ChangeStatusReq;
import com.gjq.planet.common.domain.vo.req.article.SearchArticleReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.EditArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.RecommendArticleResp;
import com.gjq.planet.common.valid.group.InsertGroup;
import com.gjq.planet.common.valid.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章信息接口")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/getArticleList")
    @ApiOperation("获取文章信息列表")
    public ApiResult<List<ArticleResp>> getArticleList() {
        return ApiResult.success(articleService.getArticleList());
    }

    @PostMapping("/searchArticleList")
    @ApiOperation("条件查询文章列表")
    public ApiResult<List<ArticleResp>> searchArticleList(@RequestBody SearchArticleReq req) {
        return ApiResult.success(articleService.searchArticleList(req));
    }

    @PlanetAdmin
    @PostMapping("/saveArticle")
    @ApiOperation("保存文章")
    public ApiResult<Void> saveArticle(@RequestBody @Validated({UpdateGroup.class}) ArticleReq req) {
        articleService.saveArticle(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @PostMapping("/publishArticle")
    @ApiOperation("发布文章")
    public ApiResult<Void> publishArticle(@RequestBody @Validated({InsertGroup.class}) ArticleReq req) {
        articleService.publish(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @PutMapping("/changeStatus")
    @ApiOperation("改变状态")
    public ApiResult<Void> changeViewStatus(@RequestBody ChangeStatusReq req) {
        articleService.changeStatus(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @PutMapping("/removeShelves/{articleId}")
    @ApiOperation("下架文章")
    public ApiResult<Void> removeShelves(@PathVariable("articleId") Long articleId) {
        articleService.removeShelves(articleId);
        return ApiResult.success();
    }

    @PlanetAdmin
    @GetMapping("/getEditArticleById/{articleId}")
    @ApiOperation("获取可编辑的文章实体")
    public ApiResult<EditArticleResp> getEditArticleById(@PathVariable("articleId") Long articleId) {
        return ApiResult.success(articleService.getEditArticleById(articleId));
    }

    @NotToken
    @ApiOperation("获取推荐文章列表")
    @GetMapping("/recommendArticleList")
    public ApiResult<List<RecommendArticleResp>> recommendArticleList() {
        return ApiResult.success(articleService.recommendArticleList());
    }

    @NotToken
    @ApiOperation("通过ID获取文章信息")
    @GetMapping("/getArticleById/{articleId}")
    public ApiResult<ArticleResp> getArticleById(@PathVariable("articleId") Long articleId) {
        return ApiResult.success(articleService.getArticleById(articleId));
    }

}

