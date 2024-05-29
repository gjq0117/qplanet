package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/15 16:33
 * @description: ws前端请求类型枚举
 */
@AllArgsConstructor
@Getter
public enum WSReqTypeEnum {

    HEARTBEAT(1,"心跳包"),
    AUTHORIZE(2,"认证请求"),
    ;

    private final Integer type;
    private final String desc;

    private static final Map<Integer, WSReqTypeEnum> cache;

    static {
        cache = Arrays.stream(WSReqTypeEnum.values()).collect(Collectors.toMap(WSReqTypeEnum::getType, Function.identity()));
    }

    public static WSReqTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
