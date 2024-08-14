package com.gjq.planet.common.valid.group;

import jakarta.validation.GroupSequence;

/**
 * @author: gjq0117
 * @date: 2024/4/22 13:46
 * @description: 参数校验分组配置
 */
@GroupSequence({InsertGroup.class, UpdateGroup.class})
public interface ValidationGroupSequenceConfig {
}
