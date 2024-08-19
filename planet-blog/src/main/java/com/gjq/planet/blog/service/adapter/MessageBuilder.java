package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.blog.service.strategy.AbstractMsgHandler;
import com.gjq.planet.blog.service.strategy.MsgHandlerFactory;
import com.gjq.planet.common.domain.dto.msg.TextMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/30 15:46
 * @description: 消息建造者
 */
public class MessageBuilder {

    /**
     * 构建消息实体
     *
     * @param request
     * @param uid
     */
    public static Message buildMsg(ChatMessageReq request, Long uid) {
        return Message.builder()
                .fromUid(uid)
                .roomId(request.getRoomId())
                .type(request.getMsgType())
                .build();
    }

    public static ChatMessageBody buildMsgResp(Message message) {
        ChatMessageBody.FromUserInfo fromUserInfo = ChatMessageBody.FromUserInfo.builder()
                .uid(message.getFromUid())
                .build();
        ChatMessageBody.MessageInfo messageInfo = ChatMessageBody.MessageInfo.builder()
                .id(message.getId())
                .roomId(message.getRoomId())
                .sendTime(message.getCreateTime().getTime())
                .type(message.getType())
                .build();
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNoNull(message.getType());
        messageInfo.setBody(msgHandler.showMsg(message));
        return ChatMessageBody.builder()
                .fromUser(fromUserInfo)
                .messageInfo(messageInfo)
                .build();
    }

    /**
     *  构建文本消息的请求
     *
     * @param roomId 房间号
     * @return
     */
    public static ChatMessageReq buildTextMsgReq(Long roomId, String content, List<Long> atList) {
        TextMsgDTO textMsg = TextMsgDTO.builder()
                .content(content)
                .atUidList(atList)
                .build();
        return ChatMessageReq.builder()
                .roomId(roomId)
                // 默认为文本消息
                .msgType(MessageTypeEnum.TEXT.getType())
                .body(textMsg)
                .build();
    }


}
