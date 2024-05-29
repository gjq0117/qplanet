package com.gjq.planet.blog.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.gjq.planet.common.domain.entity.Contact;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.RoomGroup;
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
     * @param message   消息实体
     * @return 会话响应实体
     */
    public static ContactResp buildContactResp(Contact contact, Room room, RoomGroup roomGroup, Message message) {
        ContactResp contactResp = new ContactResp();
        BeanUtil.copyProperties(roomGroup, contactResp);
        BeanUtil.copyProperties(contact, contactResp);
        if (Objects.nonNull(message)) {
            contactResp.setLastMsg(message.getContent());
        }
        Date activeTime = RoomTypeEnum.SINGLE_CHAT.getType().equals(room.getType()) ? contact.getActiveTime() : room.getActiveTime();
        // 格式化时间
        contactResp.setActiveTime(CommonUtil.formatTime(activeTime));
        return contactResp;
    }
}
