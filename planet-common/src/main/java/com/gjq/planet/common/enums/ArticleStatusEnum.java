package com.gjq.planet.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/22 12:24
 * @description: 文章状态枚举
 */
@AllArgsConstructor
@Getter
public enum ArticleStatusEnum {

    SAVE(0, "保存"),
    PUBLISH(1, "发布"),
    REMOVE_SHELVES(2, "下架");


    private Integer type;
    private String desc;

    private static final Map<Integer, ArticleStatusEnum> cache;

    static {
        cache = Arrays.stream(ArticleStatusEnum.values()).collect(Collectors.toMap(ArticleStatusEnum::getType, Function.identity()));
    }

    public static ArticleStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
