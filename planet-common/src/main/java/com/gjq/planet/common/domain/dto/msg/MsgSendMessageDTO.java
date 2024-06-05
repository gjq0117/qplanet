package com.gjq.planet.common.domain.dto.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/6/1 12:14
 * @description: 消息发送至MQ的实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgSendMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  消息ID
     */
    private Long msgId;
}
