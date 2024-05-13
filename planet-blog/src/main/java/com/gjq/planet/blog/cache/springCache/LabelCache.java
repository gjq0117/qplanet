package com.gjq.planet.blog.cache.springCache;

import com.gjq.planet.blog.dao.ArticleRelationDao;
import com.gjq.planet.blog.dao.LabelDao;
import com.gjq.planet.blog.service.ISortService;
import com.gjq.planet.common.domain.entity.Label;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/21 12:20
 * @description: 标签信息缓存
 */
@Component
public class LabelCache {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ISortService sortService;

    @Autowired
    private ArticleRelationDao articleRelationDao;

    /**
     * 设置缓存
     *
     * @return
     */
    @Cacheable(cacheNames = "LabelList")
    public List<LabelResp> getList() {
        List<Label> list = labelDao.list();
        if (Objects.isNull(list) || list.size() <= 0) {
            return null;
        }
        return list.stream()
                .sorted(Comparator.comparing(Label::getSortId))
                .map(label -> {
                    LabelResp resp = new LabelResp();
                    BeanUtils.copyProperties(label, resp);
                    // 封装分类信息
                    resp.setSortResp(sortService.getRespById(label.getSortId()));
                    // 查找该标签下的文章数
                    resp.setArticleNum(articleRelationDao.getArticleNumByLabelId(label.getId()));
                    return resp;
                }).collect(Collectors.toList());
    }

    /**
     * 删除缓存
     */
    @CacheEvict(cacheNames = "LabelList", allEntries = true)
    public void evictList() {
    }
}
