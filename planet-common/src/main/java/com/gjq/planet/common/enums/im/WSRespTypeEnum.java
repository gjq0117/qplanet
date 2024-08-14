package com.gjq.planet.common.enums.im;

import com.gjq.planet.common.domain.vo.resp.websocket.base.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/15 18:21
 * @description: ws响应实体枚举
 */
@Getter
@AllArgsConstructor
public enum WSRespTypeEnum {

    LOGIN_SUCCESS(1, "登录成功通知", WSLoginSuccess.class),
    TOKEN_VALID_FAIL(2, "token验证失败", TokenValidFail.class),
    ONLINE_OFFLINE_NOTIFY(3, "上下线通知", WSOnlineOfflineNotify.class),
    NEW_MESSAGE(4, "新消息通知", NewMessage.class),
    NEW_MEMBERS_JOINING_GROUP(5, "新人进群通知", NewMemberJoin.class),
    NEW_CONTACT(6, "新会话通知",NewContact.class),
    CALL_ROBOT(7,"回调机器人", CallRobot.class),
    ;

    private final Integer type;
    private final String desc;
    private final Class dataClass;

    private static final Map<Integer, WSRespTypeEnum> cache;

    static {
        cache = Arrays.stream(WSRespTypeEnum.values()).collect(Collectors.toMap(WSRespTypeEnum::getType, Function.identity()));
    }

    public static WSRespTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
