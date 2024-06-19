package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.event.MessageSendEvent;
import com.gjq.planet.blog.mq.producer.MqProducer;
import com.gjq.planet.common.constant.MQConstant;
import com.gjq.planet.common.domain.dto.msg.MsgSendMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author: gjq0117
 * @date: 2024/6/1 11:06
 * @description: 消息发送事件监听器
 */
@Slf4j
@Component
public class MessageSendListener {

    @Autowired
    private MqProducer mqProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
        // MQ生产者发送消息
        mqProducer.sendSecureMsg(MQConstant.SEND_MSG_TOPIC, new MsgSendMessageDTO(msgId), msgId);
    }
}
