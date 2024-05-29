package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:27
 * @description: 申请状态枚举
 */
@AllArgsConstructor
@Getter
public enum ApplyStatusEnum {

    PENDING_REVIEW(0, "待审核"),
    AGREED(1, "已同意"),
    REJECTED(2, "已拒绝"),
    ;

    private final Integer status;

    private final String desc;
}
