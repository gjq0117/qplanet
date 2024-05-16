package com.gjq.planet.common.enums.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
