package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/7 11:42
 * @description:
 */
@AllArgsConstructor
@Getter
public enum UserActiveStatusEnum {

    ONLINE(1, "在线"),
    OFFLINE(0, "离线"),
    ;

    private final Integer status;

    private final String desc;

    private static final Map<Integer, UserActiveStatusEnum> cache;

    static {
        cache = Arrays.stream(UserActiveStatusEnum.values()).collect(Collectors.toMap(UserActiveStatusEnum::getStatus, Function.identity()));
    }

    public static UserActiveStatusEnum of(Integer status) {
        return cache.get(status);
    }
}
