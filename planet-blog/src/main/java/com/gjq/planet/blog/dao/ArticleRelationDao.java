package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.common.enums.blog.ArticleRelationEnum;
import com.gjq.planet.blog.mapper.ArticleRelationMapper;
import com.gjq.planet.common.domain.entity.ArticleRelation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章多关联外键表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class ArticleRelationDao extends ServiceImpl<ArticleRelationMapper, ArticleRelation> {

    public Integer getArticleNumByLabelId(Long labelId) {
        return lambdaQuery()
                .eq(ArticleRelation::getType, ArticleRelationEnum.LABEL.getType())
                .eq(ArticleRelation::getTargetId, labelId)
                .count().intValue();
    }

    /**
     * 获取文章的关系列表
     *
     * @param articleId 文章ID
     * @param type      类型
     * @return
     */
    public List<Long> getRelationIdList(Long articleId, Integer type) {
        return lambdaQuery()
                .select(ArticleRelation::getTargetId)
                .eq(ArticleRelation::getType, type)
                .eq(ArticleRelation::getArticleId, articleId)
                .list()
                .stream()
                .map(ArticleRelation::getTargetId)
                .collect(Collectors.toList());

    }

    /**
     * 判断关系是否存在
     *
     * @param articleId 文章ID
     * @param targetId  关系对应的目标ID
     * @param type      关系类型
     * @return
     */
    public Boolean relationIsExist(Long articleId, Long targetId, Integer type) {
        return lambdaQuery()
                .eq(ArticleRelation::getType, type)
                .eq(ArticleRelation::getArticleId, articleId)
                .eq(ArticleRelation::getTargetId, targetId)
                .count() > 0;
    }

    /**
     * 通过targetId和type删除关系
     *
     * @param targetId
     * @param type
     */
    public void removeByTargetId(Long targetId, Integer type) {
        remove(new QueryWrapper<ArticleRelation>().eq("target_id", targetId).eq("type", type));
    }
}
