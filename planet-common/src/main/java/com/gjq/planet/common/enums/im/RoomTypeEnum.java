package com.gjq.planet.common.enums.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/24 18:16
 * @description: 房间类型枚举
 */
@AllArgsConstructor
@Getter
public enum RoomTypeEnum {

    SINGLE_CHAT(1, "单聊"),
    COMMON_ROOM_CHAT(2, "普通群聊"),
    ALL_STAFF_ROOM_CHAT(3, "全员群聊"),
    ROBOT_CHAT(4, "机器人"),
    ;

    private final Integer type;

    private final String desc;

    private static final Map<Integer, RoomTypeEnum> cache;

    static {
        cache = Arrays.stream(RoomTypeEnum.values()).collect(Collectors.toMap(RoomTypeEnum::getType, Function.identity()));
    }

    public static RoomTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
