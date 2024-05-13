package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.blog.utils.RequestHolder;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.article.ArticleReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.EditArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.RecommendArticleResp;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static com.gjq.planet.common.enums.YesOrNoEnum.NO;
import static com.gjq.planet.common.enums.YesOrNoEnum.YES;

/**
 * @author: gjq0117
 * @date: 2024/4/22 14:18
 * @description: 文章信息构造器
 */
public class ArticleBuilder {

    /**
     * 通过请求信息构造文章实体
     *
     * @param req
     * @return
     */
    public static Article buildFromReq(ArticleReq req) {
        Article article = new Article();
        BeanUtils.copyProperties(req, article);
        // 作者信息
        article.setUserId(RequestHolder.get().getUid());
        // 解析
        article.setCommentStatus(req.getCommentStatus() ? YES.getCode() : NO.getCode());
        article.setRecommendStatus(req.getRecommendStatus() ? YES.getCode() : NO.getCode());
        article.setViewStatus(req.getViewStatus() ? YES.getCode() : NO.getCode());
        return article;
    }

    /**
     * 构建文章响应体
     *
     * @param article       数据库中文章实体
     * @param user          用户信息
     * @param sortResp      分类信息
     * @param labelRespList 标签列表
     * @return
     */
    public static ArticleResp buildResp(Article article, User user, SortResp sortResp, List<LabelResp> labelRespList) {
        ArticleResp articleResp = new ArticleResp();
        BeanUtils.copyProperties(article, articleResp);
        articleResp.setAuthor(user.getNickname());
        articleResp.setSortResp(sortResp);
        articleResp.setLabelList(labelRespList);
        articleResp.setViewStatus(YES.getCode().equals(article.getViewStatus()));
        articleResp.setCommentStatus(YES.getCode().equals(article.getCommentStatus()));
        articleResp.setRecommendStatus(YES.getCode().equals(article.getRecommendStatus()));
        return articleResp;
    }

    /**
     * 通过文章信息构建EditArticleResp
     *
     * @param article
     * @return
     */
    public static EditArticleResp buildEditResp(Article article) {
        EditArticleResp editArticleResp = new EditArticleResp();
        BeanUtils.copyProperties(article, editArticleResp);
        editArticleResp.setViewStatus(YES.getCode().equals(article.getViewStatus()));
        editArticleResp.setCommentStatus(YES.getCode().equals(article.getCommentStatus()));
        editArticleResp.setRecommendStatus(YES.getCode().equals(article.getRecommendStatus()));
        return editArticleResp;
    }

    /**
     *  构建推荐文章信息
     *
     * @param article
     */
    public static RecommendArticleResp buildRecommendArticleResp(Article article) {
        RecommendArticleResp recommendArticleResp = new RecommendArticleResp();
        BeanUtils.copyProperties(article,recommendArticleResp);
        recommendArticleResp.setHasVideo(!StringUtils.isBlank(article.getVideoUrl()));
        return recommendArticleResp;
    }
}
