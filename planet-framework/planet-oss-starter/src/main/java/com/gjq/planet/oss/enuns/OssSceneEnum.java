package com.gjq.planet.oss.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: oss场景枚举
 * @Author: gjq
 * @Date: 2023-06-20
 */
@AllArgsConstructor
@Getter
public enum OssSceneEnum {
    BLOG(1, "博客", "/blog"),
    USER(2,"用户", "/user")
    ;

    private final Integer type;
    private final String desc;
    private final String path;

    private static final Map<Integer, OssSceneEnum> cache;

    static {
        cache = Arrays.stream(OssSceneEnum.values()).collect(Collectors.toMap(OssSceneEnum::getType, Function.identity()));
    }

    public static OssSceneEnum of(Integer type) {
        return cache.get(type);
    }
}
