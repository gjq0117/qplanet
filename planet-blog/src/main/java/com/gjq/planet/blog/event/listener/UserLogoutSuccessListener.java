package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.event.UserLogoutSuccessEvent;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:54
 * @description: 用户退出登录成功事件监听器
 */
@Component
public class UserLogoutSuccessListener {

    @Autowired
    private UserDao userDao;

    /**
     * 用户退出登录改变状态
     *
     */
    @EventListener(classes = UserLogoutSuccessEvent.class)
    public void updateStatus(UserLogoutSuccessEvent userLogoutSuccessEvent) {
        User user = userLogoutSuccessEvent.getUser();
        User update = User.builder()
                .id(user.getId())
                .isActive(UserActiveStatusEnum.OFFLINE.getStatus())
                .build();
        userDao.updateById(update);
    }
}
