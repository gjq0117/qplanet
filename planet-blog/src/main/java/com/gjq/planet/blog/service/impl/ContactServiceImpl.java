package com.gjq.planet.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.mapper.ContactMapper;
import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.blog.service.adapter.ContactBuilder;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.domain.vo.resp.websocket.base.NewContact;
import com.gjq.planet.common.utils.CursorSplitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/25 13:16
 * @description:
 */
@Service
@Slf4j
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public CursorPageBaseResp<ContactResp> getContactListPage(Long uid, ContactPageReq req) {
        // 如果是群聊就不需要维护contact表的ActiveTime
        List<ContactResp> result = new ArrayList<>();
        Boolean isLast = false;
        Pair<Boolean, String> pair = CursorSplitUtils.getBooleanPair(req.getCursor());
        Boolean groupLast = pair.getKey();
        String timeCursor = pair.getValue();
        if (!groupLast) {
            // 群会话列表
            Date time = null;
            if (StringUtils.isNotBlank(timeCursor)) {
                time = Optional.of(new Date(Long.parseLong(timeCursor))).orElse(null);
            }
            List<Contact> groupContactList = contactMapper.getGroupContact(uid, time);
            List<ContactResp> groupContactRespList = groupContactList.stream()
                    .map(this::buildContactResp)
                    .collect(Collectors.toList());
            // 最后一条
            groupLast = groupContactList.size() != req.getPageSize();
            // 如果不是最后一条，获取群聊的游标
            if (!groupLast) {
                timeCursor = Optional.ofNullable(CollectionUtil.getLast(groupContactList))
                        .map(Contact::getActiveTime)
                        .map(activeTime -> String.valueOf(activeTime.getTime()))
                        .orElse(null);
            }
            result.addAll(groupContactRespList);
        }
        if (groupLast) {
            if (!result.isEmpty()) {
                req.setPageSize(req.getPageSize() - result.size());
            }
            // 如果是空，说明群会话列表已经找完了，直接全找好友会话列表就行了
            req.setCursor(timeCursor);
            CursorPageBaseResp<Contact> cursorPageBaseResp = contactDao.getCursorPage(uid, req);
            List<ContactResp> friendContactRespList = cursorPageBaseResp.getList()
                    .stream()
                    .map(this::buildContactResp)
                    .collect(Collectors.toList());
            // 游标
            timeCursor = cursorPageBaseResp.getCursor();
            // 最后一个
            isLast = cursorPageBaseResp.getIsLast();
            result.addAll(friendContactRespList);
        }
        String cursor = CursorSplitUtils.generateBoolCursor(groupLast, timeCursor);

        return new CursorPageBaseResp<>(cursor, isLast, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void createFriendContact(Long roomId, Long uid1, Long uid2) {
        Contact contact1 = Contact.builder()
                .roomId(roomId)
                .uid(uid1)
                .build();
        Contact contact2 = Contact.builder()
                .roomId(roomId)
                .uid(uid2)
                .build();
        contactDao.save(contact1);
        contactDao.save(contact2);
    }

    @Override
    public void pushNewContact(Long roomId, Set<Long> uidSet) {
        uidSet.forEach(uid -> {
            executor.execute(() -> {
                Contact contact = contactDao.getByUidAndRoomId(uid, roomId);
                ContactResp contactResp = this.buildContactResp(contact);
                websocketService.pushMsg(new NewContact(contactResp), Collections.singleton(uid));
            });
        });
    }

    @Override
    public ContactResp getContactResp(Long uid, Long roomId) {
        Contact contact = contactDao.getByUidAndRoomId(uid, roomId);
        return buildContactResp(contact);
    }

    private ContactResp buildContactResp(Contact contact) {
        Room room = roomCache.get(contact.getRoomId());
        Message message = null;
        User friend = null;
        RoomGroup roomGroup = null;
        User lastMsgSendUser = null;
        // 获取最后一条消息
        if (room.isFriendRoom() || room.isRobotRoom()) {
            // 单聊
            message = messageDao.getById(contact.getLastMsgId());
            // 好友信息
            RoomFriend roomFriend = roomFriendDao.getByRoomId(room.getId());
            Long friendUid = roomFriend.getUid1().equals(contact.getUid()) ? roomFriend.getUid2() : roomFriend.getUid1();
            friend = userDao.getById(friendUid);
        } else {
            contact.setActiveTime(room.getActiveTime());
            // 群聊
            message = messageDao.getById(room.getLastMsgId());
            // 群聊信息（群名、群头像）
            roomGroup = roomGroupCache.get(contact.getRoomId());
        }
        if (Objects.nonNull(message)) {
            lastMsgSendUser = userDao.getById(message.getFromUid());
        }
        return ContactBuilder.buildContactResp(contact, room, roomGroup, friend, message, lastMsgSendUser);
    }
}
