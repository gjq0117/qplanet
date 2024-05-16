package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/5/1 11:57
 * @description: 访问信息类型
 */
@AllArgsConstructor
@Getter
public enum VisitTypeEnum {

    BLOG_WEB(0, "博客网"),
    BOLG_ARTICLE(1, "博客文章"),
    ;

    private Integer type;

    private String desc;
}
