package com.gjq.planet.blog.service.producer;

import com.gjq.planet.transaction.annotation.SecureInvoke;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author: gjq0117
 * @date: 2024/6/1 12:09
 * @description: MQ生产者
 */
@Component
public class MqProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通发送消息
     *
     * @param topic 主题
     * @param body  消息体
     */
    public void sendMsg(String topic, Object body) {
        Message<Object> build = MessageBuilder.withPayload(body).build();
        rocketMQTemplate.send(topic, build);
    }

    /**
     * 发送可靠消息
     *
     * @param topic 主题
     * @param body  消息体
     * @param key   key
     */
    @SecureInvoke
    public void sendSecureMsg(String topic, Object body, Object key) {
        Message<Object> build = MessageBuilder
                .withPayload(body)
                .setHeader("KEYS", key)
                .build();
        rocketMQTemplate.send(topic, build);
    }
}
