package com.gjq.planet.blog.cache.springCache;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.SortDao;
import com.gjq.planet.blog.service.ILabelService;
import com.gjq.planet.common.domain.entity.Sort;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/20 12:10
 * @description: 分类信息缓存
 */
@Component
public class SortCache {

    @Autowired
    private SortDao sortDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    @Lazy
    private ILabelService labelService;

    /**
     * 设置缓存
     *
     * @return
     */
    @Cacheable(cacheNames = "sortList")
    public List<SortResp> getSortList() {
        List<Sort> sortList = sortDao.list();
        if (Objects.isNull(sortList) || sortList.size() <= 0) {
            return null;
        }
        return sortList.stream().map(sort -> {
            SortResp sortResp = new SortResp();
            BeanUtils.copyProperties(sort, sortResp);
            // 获取该分类下的标签数
            sortResp.setLabelNum(labelService.getLabelNumBySortId(sort.getId()));
            // 获取该分类下的文章数
            sortResp.setArticleNum(articleDao.getArticleNumBySortId(sort.getId()));
            return sortResp;
        }).sorted(Comparator.comparing(SortResp::getPriority)).collect(Collectors.toList());
    }

    /**
     * 清空缓存
     */
    @CacheEvict(cacheNames = "sortList",allEntries = true)
    public void evictSortList() {
    }
}
