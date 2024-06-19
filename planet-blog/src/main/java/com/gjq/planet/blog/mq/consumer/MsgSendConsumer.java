package com.gjq.planet.blog.mq.consumer;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.blog.service.IMessageService;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.common.constant.MQConstant;
import com.gjq.planet.common.domain.dto.msg.MsgSendMessageDTO;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;
import com.gjq.planet.common.domain.vo.resp.websocket.base.NewMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/6/1 13:38
 * @description: 聊天消息的消费者
 */
@Component
@RocketMQMessageListener(topic = MQConstant.SEND_MSG_TOPIC, consumerGroup = MQConstant.SEND_MSG_GROUP)
public class MsgSendConsumer implements RocketMQListener<MsgSendMessageDTO> {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private GroupMemberCache groupMemberCache;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IContactService contactService;

    @Override
    public void onMessage(MsgSendMessageDTO dto) {
        Message message = messageDao.getById(dto.getMsgId());
        Room room = roomCache.get(message.getRoomId());
        // 推送消息
        pushMsg(room, message);
    }

    private void pushMsg(Room room, Message message) {
        // 更新最新消息
        if (Objects.isNull(room)) return;
        ChatMessageBody chatMessageResp = messageService.buildMsgResp(message.getId());
        Set<Long> uidSet = null;
        if (room.isFriendRoom()) {
            // 单聊房间（更新会话最新消息）
            RoomFriend friendRoom = roomFriendDao.getByRoomId(room.getId());
            contactDao.refreshActiveTime(room.getId(), message, Arrays.asList(friendRoom.getUid1(), friendRoom.getUid2()));
            // 推送消息给好友
            uidSet = new HashSet<>(Arrays.asList(friendRoom.getUid1(), friendRoom.getUid2()));
            websocketService.pushMsg(new NewMessage(chatMessageResp), uidSet);
        } else if (room.isGroupRoom()) {
            // 群聊房间（更新房间最新消息）
            roomDao.refreshActiveTime(room.getId(), message);
            // 更新缓存
            roomCache.put(roomDao.getById(room.getId()));
            // 推送给群聊
            if (room.isHotRoom()) {
                // 全员群聊
                websocketService.pushMsg(new NewMessage(chatMessageResp), null);
            } else {
                // 普通群聊
                RoomGroup roomGroup = roomGroupCache.get(room.getId());
                uidSet = groupMemberCache.getBatch(roomGroup.getId(), null).stream().map(GroupMember::getUid).collect(Collectors.toSet());
                websocketService.pushMsg(new NewMessage(chatMessageResp), uidSet);
            }
        }
    }
}

