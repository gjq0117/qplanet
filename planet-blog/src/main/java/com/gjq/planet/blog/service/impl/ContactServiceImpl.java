package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.blog.service.adapter.ContactBuilder;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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

    @Override
    public CursorPageBaseResp<ContactResp> getContactListPage(Long uid, ContactPageReq req) {
        // 如果是群聊就不需要维护ActiveTime，需要改造一下接口
        CursorPageBaseResp<Contact> cursorPageBaseResp = contactDao.getCursorPage(uid, req);
        AssertUtil.isNotEmpty(cursorPageBaseResp, "用户ID错误 id:" + uid);
        List<ContactResp> collect = cursorPageBaseResp.getList().stream().map(contact -> {
            Room room = roomCache.get(contact.getRoomId());
            // 获取最后一条消息
            Message message = null;
            User friend = null;
            RoomGroup roomGroup = null;
            User lastMsgSendUser = null;
            if (room.isFriendRoom()) {
                // 单聊
                message = messageDao.getById(contact.getLastMsgId());
                // 好友信息
                RoomFriend roomFriend = roomFriendDao.getByRoomId(room.getId());
                Long friendUid = roomFriend.getUid1().equals(uid) ? roomFriend.getUid2() : roomFriend.getUid1();
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
        }).sorted(Comparator.comparing(ContactResp::getActiveTime).reversed()).collect(Collectors.toList());
        return new CursorPageBaseResp<>(cursorPageBaseResp.getCursor(), cursorPageBaseResp.getIsLast(), collect);
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
}
