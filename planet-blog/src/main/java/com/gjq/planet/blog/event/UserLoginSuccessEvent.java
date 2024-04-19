package com.gjq.planet.blog.event;


import com.gjq.planet.common.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:38
 * @description: 用户登录成功事件
 */
@Getter
public class UserLoginSuccessEvent extends ApplicationEvent {

    private User user;

    public UserLoginSuccessEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
