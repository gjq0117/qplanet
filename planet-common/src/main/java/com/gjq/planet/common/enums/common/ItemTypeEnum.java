package com.gjq.planet.common.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/6/28 17:31
 * @description: 物品类型
 */
@AllArgsConstructor
@Getter
public enum ItemTypeEnum {

    IMG_EMOJI(0, "表情包"),
    ;

    private Integer type;

    private String desc;
}
