package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:30
 * @description: 消息类型枚举
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {

    TEXT(1, "文本消息"),
    RECALL(2, "撤回消息"),
    EMOJI(3, "表情消息"),
    IMG(4, "图片消息"),
    FILE(5, "文件消息"),
    SOUND(6, "语音消息"),
    VIDEO(7, "视频消息"),
    ;

    private final Integer type;

    private final String desc;

    private final static Map<Integer, MessageTypeEnum> cache;

    static {
        cache = Arrays.stream(MessageTypeEnum.values()).collect(Collectors.toMap(MessageTypeEnum::getType, Function.identity()));
    }

    public static MessageTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
