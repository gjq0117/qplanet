package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.event.UserRegisterSuccessEvent;
import com.gjq.planet.blog.service.IGroupMemberService;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.utils.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * @author: gjq0117
 * @date: 2024/4/29 11:38
 * @description: 用户注册成功事件监听器
 */
@Component
public class UserRegisterSuccessListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private IGroupMemberService groupMemberService;


    /**
     * 初始化IP信息
     */
    @TransactionalEventListener(classes = UserRegisterSuccessEvent.class, fallbackExecution = true, phase = TransactionPhase.BEFORE_COMMIT)
    public void initIpInfo(UserRegisterSuccessEvent userRegisterSuccessEvent) {
        User user = userRegisterSuccessEvent.getUser();
        ipService.refreshIpInfoAsync(user.getId(), RequestHolder.get().getIp());
    }

    /**
     * 加入大群聊
     */
    @TransactionalEventListener(classes = UserRegisterSuccessEvent.class, fallbackExecution = true, phase = TransactionPhase.BEFORE_COMMIT)
    public void intoGroup(UserRegisterSuccessEvent userRegisterSuccessEvent) {
        User user = userRegisterSuccessEvent.getUser();
        groupMemberService.joinSystemGroup(user.getId());
    }
}
