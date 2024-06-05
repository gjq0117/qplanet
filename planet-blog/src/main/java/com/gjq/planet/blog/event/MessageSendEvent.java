package com.gjq.planet.blog.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: gjq0117
 * @date: 2024/6/1 11:05
 * @description: 消息发送事件
 */
@Getter
public class MessageSendEvent extends ApplicationEvent {

    private final Long msgId;

    public MessageSendEvent(Object source, Long msgId) {
        super(source);
        this.msgId = msgId;
    }

}
