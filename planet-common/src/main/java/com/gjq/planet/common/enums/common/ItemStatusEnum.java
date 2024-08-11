package com.gjq.planet.common.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/6/28 23:00
 * @description: 物品状态枚举
 */
@AllArgsConstructor
@Getter
public enum ItemStatusEnum {

    INVALID(0, "无效"),
    EFFECTIVE(1, "有效")
    ;


    private Integer status;

    private String desc;
}
