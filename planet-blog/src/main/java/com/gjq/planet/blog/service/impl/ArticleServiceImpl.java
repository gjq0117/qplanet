package com.gjq.planet.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gjq.planet.blog.cache.redis.ArticleRespListCache;
import com.gjq.planet.blog.cache.redis.RecommendArticleListCache;
import com.gjq.planet.blog.cache.redis.SortListCache;
import com.gjq.planet.blog.cache.springCache.LabelCache;
import com.gjq.planet.blog.cache.springCache.SortCache;
import com.gjq.planet.blog.dao.*;
import com.gjq.planet.blog.exception.BusinessException;
import com.gjq.planet.blog.service.IArticleService;
import com.gjq.planet.blog.service.IVisitorService;
import com.gjq.planet.blog.service.adapter.ArticleBuilder;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.entity.ArticleRelation;
import com.gjq.planet.common.domain.entity.Sort;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.article.ArticleReq;
import com.gjq.planet.common.domain.vo.req.article.ChangeStatusReq;
import com.gjq.planet.common.domain.vo.req.article.SearchArticleReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.EditArticleResp;
import com.gjq.planet.common.domain.vo.resp.article.RecommendArticleResp;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import com.gjq.planet.common.enums.ArticleAttributeStatusEnum;
import com.gjq.planet.common.enums.ArticleRelationEnum;
import com.gjq.planet.common.enums.ArticleStatusEnum;
import com.gjq.planet.common.enums.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/22 12:20
 * @description: 文章业务类
 */
@Service
@Slf4j
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleRelationDao articleRelationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SortDao sortDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private ArticleRespListCache articleRespListCache;

    @Autowired
    private LabelCache labelCache;

    @Autowired
    private SortCache sortCache;

    @Autowired
    private RecommendArticleListCache recommendArticleListCache;

    @Autowired
    private SortListCache sortListCache;

    @Autowired
    private IVisitorService visitorService;

    @Override
    public void saveArticle(ArticleReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        req.setStatus(ArticleStatusEnum.SAVE.getType());
        // 保存到数据库
        saveOrUpdateArticle(req);
    }

    @Override
    public void publish(ArticleReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        req.setStatus(ArticleStatusEnum.PUBLISH.getType());
        // 保存到数据库
        saveOrUpdateArticle(req);
        // 上推荐
        recommendArticleListCache.setById(req.getId());
        // 上列表
        sortListCache.setById(req.getSortId());
    }

    @Override
    public List<ArticleResp> getArticleList() {
        List<ArticleResp> result = null;
        // 有redis读redis
        result = articleRespListCache.getList();
        if (Objects.nonNull(result) && result.size() > 0) {
            // redis中存在
            return result;
        }
        // 查数据库
        List<Article> articleList = articleDao.list(new QueryWrapper<Article>().orderByDesc("create_time"));
        if (Objects.nonNull(articleList) && articleList.size() > 0) {
            result = articleList.stream().map(article ->
                    this.transToResp(article)
            ).collect(Collectors.toList());
        }
        // 保存到redis
        if (Objects.nonNull(result) && result.size() > 0) {
            articleRespListCache.setList(result);
        }
        return result;
    }

    @Override
    public ArticleResp transToResp(Article article) {
        User user = null;
        SortResp sortResp = null;
        List<LabelResp> labelRespList = null;
        try {
            // user
            CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() ->
                            userDao.getById(article.getUserId())
                    , executor);
            // sort
            CompletableFuture<SortResp> sortFuture = CompletableFuture.supplyAsync(() -> {
                SortResp resp = new SortResp();
                Sort sort = sortDao.getById(article.getSortId());
                if (Objects.nonNull(sort)) {
                    BeanUtils.copyProperties(sort, resp);
                }
                return resp;

            }, executor);
            // List<Label>
            CompletableFuture<List<LabelResp>> labelListFuture = CompletableFuture.supplyAsync(() -> {
                List<Long> labelIdList = articleRelationDao.getRelationIdList(article.getId(), ArticleRelationEnum.LABEL.getType());
                if (Objects.nonNull(labelIdList) && labelIdList.size() > 0) {
                    return labelDao.listByIds(labelIdList).stream().map(label -> {
                        LabelResp labelResp = new LabelResp();
                        if (Objects.nonNull(label)) {
                            BeanUtils.copyProperties(label, labelResp);
                        }
                        return labelResp;
                    }).collect(Collectors.toList());
                }
                return null;
            }, executor);
            user = userFuture.get();
            sortResp = sortFuture.get();
            labelRespList = labelListFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("service error:" + e);
        }
        ArticleResp articleResp = ArticleBuilder.buildResp(article, user, sortResp, labelRespList);
        return articleResp;
    }

    @Override
    public void changeStatus(ChangeStatusReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        switch (ArticleAttributeStatusEnum.toType(req.getType())) {
            case VIEW_STATUS:
                // 可见状态
                changeViewStatus(req.getArticleId(), req.getStatus() ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());
                break;
            case COMMENT_STATUS:
                // 评论状态
                changeCommentStatus(req.getArticleId(), req.getStatus() ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());
                break;
            case RECOMMEND_STATUS:
                // 推荐状态
                changeRecommendStatus(req.getArticleId(), req.getStatus() ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());
                // 改缓存
                if (req.getStatus()) {
                    recommendArticleListCache.setById(req.getArticleId());
                } else {
                    recommendArticleListCache.removeByIds(req.getArticleId());
                }
                break;
            default:
                throw new BusinessException("不存在的文章状态属性");
        }
        // 更新指定ID缓存
        articleRespListCache.setById(req.getArticleId());
        sortListCache.setById(articleDao.getById(req.getArticleId()).getSortId());
    }

    @Override
    public void removeShelves(Long articleId) {
        articleDao.removeShelves(articleId);
        // 重新设置缓存
        articleRespListCache.setById(articleId);
        sortListCache.removeShelves(articleId);
        recommendArticleListCache.removeByIds(articleId);

    }

    @Override
    public List<ArticleResp> searchArticleList(SearchArticleReq req) {
        if (Objects.isNull(req)) {
            return null;
        }
        List<ArticleResp> articleList = this.getArticleList();
        if (Objects.isNull(articleList) || articleList.size() <= 0) {
            return null;
        }
        return articleList.stream().filter(articleResp -> {
            return (Objects.isNull(req.getViewStatus()) || req.getViewStatus().equals(articleResp.getViewStatus())) &&
                    (Objects.isNull(req.getCommentStatus()) || req.getCommentStatus().equals(articleResp.getCommentStatus())) &&
                    (Objects.isNull(req.getRecommendStatus()) || req.getRecommendStatus().equals(articleResp.getRecommendStatus())) &&
                    (Objects.isNull(req.getSortId()) || req.getSortId().equals(articleResp.getSortResp().getId())) &&
                    (StringUtils.isBlank(req.getSearchKey()) || (articleResp.getAuthor().contains(req.getSearchKey())) || articleResp.getArticleTitle().contains(req.getSearchKey()));

        }).collect(Collectors.toList());
    }

    @Override
    public EditArticleResp getEditArticleById(Long articleId) {
        if (Objects.isNull(articleId)) {
            return null;
        }
        EditArticleResp result = null;
        Article article = articleDao.getById(articleId);
        if (Objects.nonNull(article)) {
            result = ArticleBuilder.buildEditResp(article);
            // 获取标签列表
            List<Long> labelIdList = articleRelationDao.getRelationIdList(articleId, ArticleRelationEnum.LABEL.getType());
            if (Objects.nonNull(labelIdList) && labelIdList.size() > 0) {
                result.setLabelIdList(labelIdList);
            }
        }
        return result;
    }

    @Override
    public List<RecommendArticleResp> recommendArticleList() {
        List<RecommendArticleResp> result = null;
        // 先从缓存中获取
        result = recommendArticleListCache.getList();
        if (Objects.nonNull(result) && result.size() > 0) {
            return result;
        }
        // 查库
        List<Article> articleList = articleDao.getRecommendArticle();
        if (Objects.nonNull(articleList) && articleList.size() > 0) {
            result = articleList.stream().map(article -> {
                RecommendArticleResp recommendArticleResp = new RecommendArticleResp();
                if (Objects.nonNull(article)) {
                    recommendArticleResp = ArticleBuilder.buildRecommendArticleResp(article);
                }
                return recommendArticleResp;
            }).collect(Collectors.toList());
        }
        // 存缓存
        recommendArticleListCache.setList(result);
        return result;
    }

    @Override
    public ArticleResp getArticleById(Long articleId) {
        if (Objects.isNull(articleId)) {
            return null;
        }
        // 先读缓存
        ArticleResp cache = articleRespListCache.getById(articleId);
        if (Objects.nonNull(cache)) {
            // 热度+1
            addViewCount(cache);
            return cache;
        }
        // 没有就读库
        Article article = articleDao.getById(articleId);
        ArticleResp articleResp = this.transToResp(article);
        // 设置缓存
        articleRespListCache.setOne(articleResp);
        // 热度+1
        addViewCount(articleResp);
        return articleResp;
    }

    /**
     * 判断文章是否可见（status = 发布,viewStatus = 1）
     *
     * @param article
     * @return
     */
    @Override
    public Boolean isView(ArticleResp article) {
        return ArticleStatusEnum.PUBLISH.getType().equals(article.getStatus()) && article.getViewStatus();
    }

    /**
     * 文章热地+1
     *
     * @param res
     */
    private void addViewCount(ArticleResp res) {
        Article update = Article.builder()
                .id(res.getId())
                .viewCount(Optional.ofNullable(res.getViewCount()).orElse(0L) + 1)
                .build();
        articleDao.updateById(update);
        visitorService.saveArticleVisitInfo(res.getId());
    }

    /**
     * 改变文章推荐状态
     *
     * @param articleId
     * @param status
     */
    private void changeRecommendStatus(Long articleId, Integer status) {
        articleDao.changeRecommendStatus(articleId, status);
    }

    /**
     * 改变文章评论状态
     *
     * @param articleId
     * @param status
     */
    private void changeCommentStatus(Long articleId, Integer status) {
        articleDao.changeCommentStatus(articleId, status);
    }

    /**
     * 改变文章可见状态
     *
     * @param articleId
     * @param status
     */
    private void changeViewStatus(Long articleId, Integer status) {
        articleDao.changeViewStatus(articleId, status);
    }

    /**
     * 保存或者更新文章信息到数据库
     *
     * @param req
     */
    public void saveOrUpdateArticle(ArticleReq req) {
        Article article = ArticleBuilder.buildFromReq(req);
        if (ArticleStatusEnum.PUBLISH.getType().equals(req.getStatus())) {
            article.setPublishTime(new Date());
        }
        if (Objects.isNull(article.getId())) {
            articleDao.save(article);
        } else {
            articleDao.updateById(article);
        }
        //更新文章与标签关系
        List<Long> labelIdList = Optional.ofNullable(req.getLabelIdList()).orElse(new ArrayList<>());
        List<Long> relationIdList = Optional.ofNullable(articleRelationDao.getRelationIdList(article.getId(), ArticleRelationEnum.LABEL.getType())).orElse(new ArrayList<>());
        updateRelationList(article.getId(), labelIdList, relationIdList, ArticleRelationEnum.LABEL);
        // 删除分类、标签等缓存信息...
        sortCache.evictSortList();
        labelCache.evictList();
        // 保存文章信息缓存
        articleRespListCache.setById(article.getId());
    }

    /**
     * 更新文章多关系列表
     *
     * @param articleId       文章ID
     * @param reqRelationList 请求携带的关系列表
     * @param dbRelationList  数据库中的关系列表
     * @param relationEnums   关系枚举
     */
    public void updateRelationList(Long articleId, List<Long> reqRelationList, List<Long> dbRelationList, ArticleRelationEnum relationEnums) {
        // 前端 1、2、4  (去掉交集新增)
        // DB  1、2、3 (去掉交集删除)
        // 取交集
        List<Long> collect = reqRelationList.stream().filter(l -> dbRelationList.contains(l)).collect(Collectors.toList());
        for (Long labelId :
                reqRelationList) {
            if (!collect.contains(labelId)) {
                // 新增
                ArticleRelation relation = ArticleRelation.builder()
                        .type(ArticleRelationEnum.LABEL.getType())
                        .targetId(labelId)
                        .articleId(articleId)
                        .build();
                articleRelationDao.save(relation);
            }
        }
        for (Long labelId :
                dbRelationList) {
            if (!collect.contains(labelId)) {
                // 删除
                articleRelationDao.removeByTargetId(labelId, relationEnums.getType());
            }
        }
    }
}
