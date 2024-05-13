package com.gjq.planet.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/13 17:44
 * @description: 0 : no  1: yes
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否");

    private Integer code;
    private String desc;

    private static final Map<Integer, YesOrNoEnum> cache;

    static {
        cache = Arrays.stream(YesOrNoEnum.values()).collect(Collectors.toMap(YesOrNoEnum::getCode, Function.identity()));
    }

    public static YesOrNoEnum of(Integer code) {
        return cache.get(code);
    }


}
