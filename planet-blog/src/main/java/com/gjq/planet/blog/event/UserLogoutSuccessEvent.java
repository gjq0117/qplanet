package com.gjq.planet.blog.event;

import com.gjq.planet.common.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:53
 * @description: 用户退出登录成功事件
 */
@Getter
public class UserLogoutSuccessEvent extends ApplicationEvent {

    private User user;

    public UserLogoutSuccessEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
