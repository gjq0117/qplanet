package com.gjq.planet.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/4/22 14:33
 * @description: 文章多关联关系类型
 */
@Getter
@AllArgsConstructor
public enum ArticleRelationEnum {

    LABEL(0, "标签"),
    ;

    private Integer type;

    private String desc;
}
