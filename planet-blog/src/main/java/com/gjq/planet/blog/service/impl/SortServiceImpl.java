package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.SortListCache;
import com.gjq.planet.blog.cache.springCache.LabelCache;
import com.gjq.planet.blog.cache.springCache.SortCache;
import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.LabelDao;
import com.gjq.planet.blog.dao.SortDao;
import com.gjq.planet.blog.service.IArticleService;
import com.gjq.planet.blog.service.ILabelService;
import com.gjq.planet.blog.service.ISortService;
import com.gjq.planet.blog.service.adapter.SortBuilder;
import com.gjq.planet.common.enums.blog.ArticleStatusEnum;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.entity.Sort;
import com.gjq.planet.common.domain.vo.req.sort.SortReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import com.gjq.planet.common.domain.vo.resp.sort.HasArticleSortResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortPageResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/19 22:35
 * @description: Sort分类相关业务
 */
@Service
public class SortServiceImpl implements ISortService {

    @Autowired
    private SortDao sortDao;

    @Autowired
    private SortCache sortCache;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    @Lazy
    private IArticleService articleService;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    @Lazy
    private LabelCache labelCache;

    @Autowired
    @Lazy
    private ILabelService labelService;

    @Autowired
    private SortListCache sortListCache;

    @Override
    public void insertOne(SortReq req) {
        AssertUtil.isNotEmpty(req, "新增一条分类信息时，请求信息为空");
        Sort sort = SortBuilder.buildFormReq(req);
        boolean success = sortDao.save(sort);
        // 清空缓存
        if (success) {
            sortCache.evictSortList();
        }
    }

    @Override
    public List<SortResp> list() {
        return sortCache.getSortList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long sortId) {
        AssertUtil.isNotEmpty(sortId, "通过sortId删除分类信息时发生错误");
        // 该分类下所有的文章都下架且文章绑定的分类ID置空
        sortListCache.removeByIds(sortId);
        List<Article> articleList = articleDao.getListBySortId(sortId);
        for (Article article :
                articleList) {
            // 下架文章
            articleService.removeShelves(article.getId());
            // 置空sort关系
            articleDao.removeSortRelation(article.getId());
        }
        // 先删除该分类下所有的标签信息
        if (labelDao.removeBySortId(sortId)) {
            // 清空缓存
            labelCache.evictList();
        }
        if (sortDao.removeById(sortId)) {
            sortCache.evictSortList();
        }
    }

    @Override
    public void updateById(SortReq req) {
        AssertUtil.isNotEmpty(req, "更新一条分类信息时，请求信息为空");
        AssertUtil.isNotEmpty(req.getId(), "更新一条分类信息时，分类id为空");
        Sort sort = SortBuilder.buildFormReq(req);
        boolean success = sortDao.updateById(sort);
        if (success) {
            sortCache.evictSortList();
        }
        sortListCache.setById(req.getId());
    }

    @Override
    public List<SortResp> searchByKey(String searchKey) {
        return sortCache.getSortList().stream().filter(sortResp ->
                sortResp.getSortName().contains(searchKey) || sortResp.getSortDescription().contains(searchKey)
        ).collect(Collectors.toList());
    }

    @Override
    public SortResp getRespById(Long sortId) {
        AssertUtil.isNotEmpty(sortId, "通过分类Id查找分类信息时，分类id为空");
        SortResp sortResp = new SortResp();
        Sort sort = sortDao.getById(sortId);
        if (Objects.isNull(sort)) {
            return sortResp;
        }
        BeanUtils.copyProperties(sort, sortResp);
        // 封装该分类下拥有的文章数
        sortResp.setArticleNum(articleDao.getArticleNumBySortId(sortId));
        return sortResp;
    }

    @Override
    public List<HasArticleSortResp> getSortAndNewArticleForSix() {
        List<HasArticleSortResp> sortAndArticle = this.getSortAndArticle();
        // 每个分类取前六个
        return sortAndArticle.stream().limit(6).collect(Collectors.toList());
    }

    @Override
    public List<HasArticleSortResp> getSortAndArticle() {
        List<HasArticleSortResp> result = null;
        // 查缓存
        result = sortListCache.getList();
        if (Objects.nonNull(result) && !result.isEmpty()) {
            return result;
        }
        // 查库
        List<SortResp> sortList = this.list();
        if (Objects.isNull(sortList) || sortList.isEmpty()) {
            return result;
        }
        List<SortResp> hasArticleSort = sortList.stream().filter(sortResp ->
                sortResp.getArticleNum() > 0
        ).collect(Collectors.toList());
        if (!hasArticleSort.isEmpty()) {
            result = hasArticleSort.stream().map(sortResp -> {
                HasArticleSortResp hasArticleSortResp = new HasArticleSortResp();
                BeanUtils.copyProperties(sortResp, hasArticleSortResp);
                // 获取该标签下所有已发布且可见的文章
                if (Objects.nonNull(sortResp.getId())) {
                    List<ArticleResp> collect = articleService.getArticleList().stream()
                            .filter(articleListResp -> articleService.isView(articleListResp) && sortResp.getId().equals(articleListResp.getSortResp().getId()))
                            .sorted(Comparator.comparing(ArticleResp::getPublishTime).reversed())
                            .collect(Collectors.toList());
                    hasArticleSortResp.setArticleRespList(collect);
                }
                return hasArticleSortResp;
            }).collect(Collectors.toList());
        }
        // 存缓存
        sortListCache.setList(result);
        return result;
    }

    @Override
    public SortPageResp getSortPageResp(Long sortId, Long labelId) {
        SortPageResp result = null;
        List<ArticleResp> articleList = articleService.getArticleList();
        if (Objects.nonNull(articleList) && !articleList.isEmpty()) {
            // 需要返回的文章
            List<ArticleResp> articleRespList = articleList.stream().filter(articleResp -> {
                boolean sortFlag = Objects.nonNull(articleResp.getSortResp()) && sortId.equals(articleResp.getSortResp().getId());
                boolean labelFlag = true;
                List<LabelResp> labelList = articleResp.getLabelList();
                if (Objects.nonNull(labelId) && Objects.nonNull(labelList)) {
                    List<Long> labelIdList = labelList.stream().map(LabelResp::getId).collect(Collectors.toList());
                    labelFlag = labelIdList.contains(labelId);
                }
                return sortFlag && labelFlag;
            }).filter(articleResp -> ArticleStatusEnum.PUBLISH.getType().equals(articleResp.getStatus())).collect(Collectors.toList());
            SortResp sortResp = getRespById(sortId);
            List<LabelResp> labelRespList = new ArrayList<>();
            if (Objects.nonNull(labelId)) {
                // 前端传了labelId就查指定Id的标签
                labelRespList.add(labelService.getById(labelId));
            } else {
                labelRespList.addAll(labelService.getListBySortId(sortId));
            }
            result = SortBuilder.buildSortPageResp(articleRespList, sortResp, labelRespList);
        }
        return result;
    }

}
