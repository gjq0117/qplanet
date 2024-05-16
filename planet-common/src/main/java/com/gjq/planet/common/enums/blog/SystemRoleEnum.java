package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/14 11:15
 * @description: 系统角色枚举
 */
@AllArgsConstructor
@Getter
public enum SystemRoleEnum {

    SUPER_ADMIN(0, "admin/站长"),
    ADMIN(1, "管理人员"),
    CUSTOMER(2, "用户");

    private Integer code;
    private String desc;

    private static final Map<Integer, SystemRoleEnum> cache;

    static {
        cache = Arrays.stream(SystemRoleEnum.values()).collect(Collectors.toMap(SystemRoleEnum::getCode, Function.identity()));
    }

    public static SystemRoleEnum of(Integer type) {
        return cache.get(type);
    }

}
