package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/29 13:57
 * @description: 性别枚举
 */
@AllArgsConstructor
@Getter
public enum GenderEnum {

    UNKNOWN(0, "薛定谔的猫"),
    MAN(1, "男"),
    WOMAN(2, "女");

    private Integer type;
    private String desc;

    private static final Map<Integer, GenderEnum> cache;

    static {
        cache = Arrays.stream(GenderEnum.values()).collect(Collectors.toMap(GenderEnum::getType, Function.identity()));
    }

    public static GenderEnum of(Integer type) {
        return cache.get(type);
    }

}
