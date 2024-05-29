package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.blog.service.adapter.ContactBuilder;
import com.gjq.planet.common.domain.entity.Contact;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.RoomGroup;
import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import com.gjq.planet.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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
    private ThreadPoolExecutor executor;


    @Override
    public CursorPageBaseResp<ContactResp> getContactListPage(Long uid, ContactPageReq req) {
        CursorPageBaseResp<Contact> cursorPageBaseResp = contactDao.getCursorPage(uid, req);
        AssertUtil.isNotEmpty(cursorPageBaseResp, "用户ID错误 id:" + uid);
        List<ContactResp> collect = cursorPageBaseResp.getList().stream().map(contact -> {
            // 异步获取房间信息
            CompletableFuture<Room> roomCompletableFuture = CompletableFuture.supplyAsync(() ->
                            roomCache.get(contact.getRoomId())
                    , executor);
            // 异步获取群组信息
            CompletableFuture<RoomGroup> roomGroupCompletableFuture = CompletableFuture.supplyAsync(() ->
                            roomGroupCache.get(contact.getRoomId())
                    , executor);

            try {
                Room room = roomCompletableFuture.get();
                Message message;
                if (RoomTypeEnum.SINGLE_CHAT.getType().equals(room.getType())) {
                    // 单聊
                    message = messageDao.getById(contact.getLastMsgId());
                } else {
                    // 群聊
                    message = messageDao.getById(room.getLastMsgId());
                }
                RoomGroup roomGroup = roomGroupCompletableFuture.get();
                return ContactBuilder.buildContactResp(contact, room, roomGroup, message);
            } catch (Exception e) {
                log.error("getContactListPage(),异步获取信息时出现异常:{}", e.getMessage());
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return new CursorPageBaseResp<>(cursorPageBaseResp.getCursor(), cursorPageBaseResp.getIsLast(), collect);
    }
}
