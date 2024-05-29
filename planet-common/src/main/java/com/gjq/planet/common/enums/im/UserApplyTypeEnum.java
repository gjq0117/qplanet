package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:08
 * @description: 用户申请枚举
 */
@AllArgsConstructor
@Getter
public enum UserApplyTypeEnum {

    FRIEND_APPLY(0, "好友申请"),
    ;

    private final Integer type;

    private final String desc;

    private static final Map<Integer, UserApplyTypeEnum> cache;

    static {
        cache = Arrays.stream(UserApplyTypeEnum.values()).collect(Collectors.toMap(UserApplyTypeEnum::getType, Function.identity()));
    }

    public static UserApplyTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
