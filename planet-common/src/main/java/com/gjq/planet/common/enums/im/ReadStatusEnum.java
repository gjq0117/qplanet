package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:33
 * @description: 已读状态
 */
@AllArgsConstructor
@Getter
public enum ReadStatusEnum {

    UNREAD(0, "未读"),
    READ(1, "已读");

    private final Integer status;

    private final String desc;
}
