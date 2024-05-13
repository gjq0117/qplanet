package com.gjq.planet.blog.service;


import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.vo.req.article.ArticleReq;
import com.gjq.planet.common.domain.vo.req.article.ChangeStatusReq;
import com.gjq.planet.common.domain.vo.req.article.SearchArticleReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.EditArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.RecommendArticleResp;

import java.util.List;

/**
 * <p>
 * 文章信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
public interface IArticleService {

    /**
     * 保存文章
     *
     * @param req
     */
    void saveArticle(ArticleReq req);

    /**
     * 发布文章
     *
     * @param req
     */
    void publish(ArticleReq req);

    /**
     * 获取文章信息列表
     *
     * @return
     */
    List<ArticleResp> getArticleList();

    /**
     * 改变文章状态
     *
     * @param req
     */
    void changeStatus(ChangeStatusReq req);

    /**
     * 下架文章
     *
     * @param articleId
     */
    void removeShelves(Long articleId);

    /**
     * 条件查询文章信息
     *
     * @param req
     * @return
     */
    List<ArticleResp> searchArticleList(SearchArticleReq req);

    /**
     * 通过id获取可编辑的文章实体信息
     *
     * @param article
     * @return
     */
    EditArticleResp getEditArticleById(Long article);

    /**
     * 获取推荐文章列表
     *
     * @return
     */
    List<RecommendArticleResp> recommendArticleList();

    /**
     * 通过ID获取文章信息
     *
     * @param articleId
     * @return
     */
    ArticleResp getArticleById(Long articleId);

    /**
     * 判断文章是否可见（status = 发布,viewStatus = 1）
     *
     * @param article
     * @return
     */
    Boolean isView(ArticleResp article);

    /**
     * 将文章信息转换成文章响应实体
     *
     * @param article
     * @return
     */
    ArticleResp transToResp(Article article);
}
