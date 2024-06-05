package com.gjq.planet.common.constant;

/**
 * @author: gjq0117
 * @date: 2024/6/1 12:15
 * @description: MQ常量
 */
public interface MQConstant {

    /**
     * 消息发送的主题
     */
    String SEND_MSG_TOPIC = "chat_send_msg_topic";

    /**
     * 消息发送的消费者组
     */
    String SEND_MSG_GROUP = "chat_send_msg_group";
}
