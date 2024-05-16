package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author: gjq0117
 * @date: 2024/4/23 14:54
 * @description: 文章属性状态枚举
 */
@AllArgsConstructor
@Getter
public enum ArticleAttributeStatusEnum {

    VIEW_STATUS(1, "可见状态"),
    COMMENT_STATUS(2, "评论状态"),
    RECOMMEND_STATUS(3, "推荐状态"),
    ;


    private Integer type;

    private String desc;

    public static ArticleAttributeStatusEnum toType(Integer type) {
        return Stream.of(ArticleAttributeStatusEnum.values())
                .filter(a -> a.type.equals(type))
                .findAny()
                .orElse(null);
    }
}
