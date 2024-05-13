package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.ArticleRelation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/22 14:37
 * @description: 文章多关联关系构造器
 */
public class ArticleRelationBuilder {

    /**
     * 构建文章与标签多关系列表
     *
     * @param labelIdList
     * @param articleId
     * @param type
     * @return
     */
    public static List<ArticleRelation> buildLabelRelationList(List<Long> labelIdList, Long articleId, Integer type) {
        return labelIdList.stream().map(labelId ->
                ArticleRelation.builder()
                        .articleId(articleId)
                        .targetId(labelId)
                        .type(type)
                        .build()
        ).collect(Collectors.toList());
    }
}
