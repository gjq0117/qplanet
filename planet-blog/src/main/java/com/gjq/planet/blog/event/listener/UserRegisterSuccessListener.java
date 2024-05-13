package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.event.UserRegisterSuccessEvent;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.blog.utils.RequestHolder;
import com.gjq.planet.common.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:38
 * @description: 用户注册成功事件监听器
 */
@Component
public class UserRegisterSuccessListener {

    @Autowired
    private IpService ipService;

    /**
     * 初始化IP信息
     *
     * @param userRegisterSuccessEvent
     */
    @EventListener(classes = UserRegisterSuccessEvent.class)
    public void initIpInfo(UserRegisterSuccessEvent userRegisterSuccessEvent) {
        User user = userRegisterSuccessEvent.getUser();
        ipService.refreshIpInfoAsync(user.getId(), RequestHolder.get().getIp());
    }
}
