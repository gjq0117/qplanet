package com.gjq.planet.blog.event;

import com.gjq.planet.common.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:36
 * @description: 用户注册成功事件
 */
@Getter
public class UserRegisterSuccessEvent extends ApplicationEvent {

    private User user;

    public UserRegisterSuccessEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
