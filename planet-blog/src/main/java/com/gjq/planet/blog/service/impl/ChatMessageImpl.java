package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.GroupMemberDao;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.event.MessageSendEvent;
import com.gjq.planet.blog.service.IMessageService;
import com.gjq.planet.blog.service.IUserFriendService;
import com.gjq.planet.blog.service.adapter.MessageBuilder;
import com.gjq.planet.blog.service.strategy.AbstractMsgHandler;
import com.gjq.planet.blog.service.strategy.MsgHandlerFactory;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessagePageReq;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;
import com.gjq.planet.common.exception.BusinessException;
import com.gjq.planet.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/29 20:04
 * @description:
 */
@Service
@Slf4j
public class ChatMessageImpl implements IMessageService {

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private GroupMemberDao groupMemberDao;

    @Autowired
    private IUserFriendService userFriendService;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Long sendMsg(Long uid, ChatMessageReq req) {
        // 统一检查
        check(uid, req);
        // 获取消息处理器
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNoNull(req.getMsgType());
        // 不同消息分类检查并保存
        Long msgId = msgHandler.checkAndSaveMsg(req, uid);
        // 发布消息
        eventPublisher.publishEvent(new MessageSendEvent(this, msgId));
        return msgId;
    }

    @Override
    public ChatMessageBody buildMsgResp(Long msgId) {
        Message message = messageDao.getById(msgId);
        return MessageBuilder.buildMsgResp(message);
    }

    @Override
    public CursorPageBaseResp<ChatMessageBody> PageMsg(ChatMessagePageReq req, Long currId) {
        AssertUtil.isTrue(Objects.nonNull(req.getRoomId()), "房间号不能为空");
        AssertUtil.isNotEmpty(currId, "登录失效");
        CursorPageBaseResp<Message> cursorPage = messageDao.getCursorPage(req.getRoomId(), req);
        List<ChatMessageBody> bodyList = cursorPage.getList().stream().map(MessageBuilder::buildMsgResp).collect(Collectors.toList());
        return new CursorPageBaseResp<>(cursorPage.getCursor(), cursorPage.getIsLast(), bodyList);
    }

    private void check(Long uid, ChatMessageReq req) {
        //1、是否存在房间
        Room room = roomCache.get(req.getRoomId());
        AssertUtil.isNotEmpty(room, "房间不存在");
        if (room.isFriendRoom()) {
            //2、如果是单聊，判断是否是好友（在user_friend这个表里看有没有这条数据）
            RoomFriend roomFriend = roomFriendDao.getByRoomId(room.getId());
            Boolean isFriend = userFriendService.isFriend(roomFriend.getUid1(), roomFriend.getUid2());
            AssertUtil.isTrue(isFriend, "你们目前不是好友关系噢~");
            AssertUtil.isTrue(uid.equals(roomFriend.getUid1()) || uid.equals(roomFriend.getUid2()), "你们目前不是好友关系噢~");
        } else if (room.isGroupRoom()) {
            //3、如果是群聊（普通群里/全员群聊），判断是否在还在群里面
            RoomGroup roomGroup = roomGroupCache.get(room.getId());
            GroupMember groupMember = groupMemberDao.getByGroupIdAndUid(roomGroup.getId(), uid);
            AssertUtil.isNotEmpty(groupMember, "你已不是群成员了噢~");
        } else {
            throw new BusinessException("房间类型错误，roomType：" + room.getType());
        }
    }
}
