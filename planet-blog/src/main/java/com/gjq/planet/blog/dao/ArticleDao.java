package com.gjq.planet.blog.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.ArticleMapper;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.enums.blog.ArticleStatusEnum;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class ArticleDao extends ServiceImpl<ArticleMapper, Article> {

    public Integer getArticleNumBySortId(Long sortId) {
        return lambdaQuery()
                .eq(Article::getSortId, sortId)
                .count();
    }

    public Boolean changeViewStatus(Long articleId, Integer status) {
        return lambdaUpdate()
                .set(Article::getViewStatus, status)
                .eq(Article::getId, articleId)
                .update();
    }

    public Boolean changeCommentStatus(Long articleId, Integer status) {
        return lambdaUpdate()
                .set(Article::getCommentStatus, status)
                .eq(Article::getId, articleId)
                .update();
    }

    public Boolean changeRecommendStatus(Long articleId, Integer status) {
        return lambdaUpdate()
                .set(Article::getRecommendStatus, status)
                .eq(Article::getId, articleId)
                .update();
    }

    public Boolean removeShelves(Long articleId) {
        return lambdaUpdate()
                .set(Article::getStatus, ArticleStatusEnum.REMOVE_SHELVES.getType())
                .eq(Article::getId, articleId)
                .update();
    }

    /**
     * 获取推荐的文章
     *
     * @return
     */
    public List<Article> getRecommendArticle() {
        return lambdaQuery()
                .eq(Article::getViewStatus, YesOrNoEnum.YES.getCode())
                .eq(Article::getRecommendStatus, YesOrNoEnum.YES.getCode())
                .eq(Article::getStatus, ArticleStatusEnum.PUBLISH.getType())
                .orderByDesc(Article::getPublishTime)
                .list();
    }

    /**
     * 通过分类ID查找文章列表
     *
     * @param sortId
     * @return
     */
    public List<Article> getListBySortId(Long sortId) {
        return lambdaQuery()
                .eq(Article::getSortId, sortId)
                .list();
    }

    /**
     * 解除文章与分类的关系
     *
     * @param article
     */
    public void removeSortRelation(Long article) {
        lambdaUpdate()
                .set(Article::getSortId, null)
                .eq(Article::getId, article)
                .update();
    }

    /**
     *  获取已发布的文章数量
     *
     * @return
     */
    public Integer getPublishCount() {
        return lambdaQuery()
                .eq(Article::getStatus,ArticleStatusEnum.PUBLISH.getType())
                .count();
    }
}
