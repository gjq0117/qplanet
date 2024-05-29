package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/24 19:28
 * @description: 群聊角色类型
 */
@AllArgsConstructor
@Getter
public enum ChatGroupRoleEnum {

    COMMON_MEMBER(0, "普通成员"),
    GROUP_MANAGE(1, "群管理"),
    GROUP_LEADER(2, "群主");

    private final Integer type;

    private final String desc;


    private static final Map<Integer, ChatGroupRoleEnum> cache;

    static {
        cache = Arrays.stream(ChatGroupRoleEnum.values()).collect(Collectors.toMap(ChatGroupRoleEnum::getType, Function.identity()));
    }

    public static ChatGroupRoleEnum of(Integer type) {
        return cache.get(type);
    }
}
