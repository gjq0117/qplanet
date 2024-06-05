package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;
import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author: gjq0117
 * @date: 2024/6/1 15:15
 * @description: 新消息
 */

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewMessage extends WsBaseResp {

    /**
     *  消息体
     */
    private ChatMessageBody msgBody;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.NEW_MESSAGE.getType();
    }
}
