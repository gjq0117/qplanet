package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.blog.service.strategy.AbstractMsgHandler;
import com.gjq.planet.blog.service.strategy.MsgHandlerFactory;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import com.gjq.planet.common.utils.CommonUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/25 13:40
 * @description: 回话列表建造器
 */
public class ContactBuilder {

    /**
     * 构建会话响应实体
     *
     * @param contact   会话信息
     * @param room      房间信息
     * @param roomGroup 房间群聊信息
     * @param friend    好友
     * @param message   消息实体
     * @param lastMsgSendUser 最后一条消息发送人的姓名
     * @return 会话响应实体
     */
    public static ContactResp buildContactResp(Contact contact, Room room, RoomGroup roomGroup, User friend, Message message, User lastMsgSendUser) {
        ContactResp contactResp = ContactResp.builder()
                .id(contact.getId())
                .roomId(room.getId())
                .readTime(contact.getReadTime())
                .lastMsgSendName(Objects.nonNull(lastMsgSendUser) ? lastMsgSendUser.getNickname() : "")
                .build();
        // 设置消息
        if (Objects.nonNull(message)) {
            AbstractMsgHandler<?> handler = MsgHandlerFactory.getStrategyNoNull(message.getType());
            contactResp.setLastMsg(handler.showContactMsg(message));
        }
        // 最后活跃时间
        Date activeTime = RoomTypeEnum.SINGLE_CHAT.getType().equals(room.getType()) ? contact.getActiveTime() : room.getActiveTime();
        if (Objects.nonNull(activeTime)) {
            contactResp.setActiveTime(CommonUtil.formatTime(activeTime));
        }

        if (Objects.nonNull(friend)) {
            // 好友会话
            contactResp.setAvatar(friend.getAvatar());
            contactResp.setName(friend.getNickname());
        } else if (Objects.nonNull(roomGroup)) {
            // 群聊会话
            contactResp.setAvatar(roomGroup.getAvatar());
            contactResp.setName(roomGroup.getName());
        }
        return contactResp;
    }
}
