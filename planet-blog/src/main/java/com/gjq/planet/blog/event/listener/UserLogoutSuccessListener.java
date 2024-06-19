package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.event.UserLogoutSuccessEvent;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:54
 * @description: 用户退出登录成功事件监听器
 */
@Component
public class UserLogoutSuccessListener {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WebsocketService websocketService;

    /**
     * 用户退出登录改变状态
     *
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = UserLogoutSuccessEvent.class, fallbackExecution = true)
    public void updateStatus(UserLogoutSuccessEvent userLogoutSuccessEvent) {
        User user = userLogoutSuccessEvent.getUser();
        User update = User.builder()
                .id(user.getId())
                .isActive(UserActiveStatusEnum.OFFLINE.getStatus())
                .build();
        userDao.updateById(update);
    }

    /**
     *  使websocket断连
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = UserLogoutSuccessEvent.class, fallbackExecution = true)
    public void wsOffline(UserLogoutSuccessEvent userLogoutSuccessEvent) {
        User user = userLogoutSuccessEvent.getUser();
        websocketService.userOffline(user.getId());
    }
}
