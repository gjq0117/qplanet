package com.gjq.planet.blog.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: gjq0117
 * @date: 2024/6/6 19:24
 * @description: 用户信息修改事件
 */
@Getter
public class UserInfoModifyEvent extends ApplicationEvent {

    /**
     *  用户iD
     */
    private Long uid;

    public UserInfoModifyEvent(Object source, Long uid) {
        super(source);
        this.uid = uid;
    }
}
