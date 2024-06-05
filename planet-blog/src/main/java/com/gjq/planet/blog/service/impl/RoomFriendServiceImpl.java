package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.service.IContactService;
import com.gjq.planet.blog.service.IRoomFriendService;
import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.RoomFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: gjq0117
 * @date: 2024/6/3 16:59
 * @description:
 */
@Service
public class RoomFriendServiceImpl implements IRoomFriendService {

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IContactService contactService;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Long createFriendRoom(Long uid1, Long uid2) {
        // 1、创建通用房间
        Long roomId = roomService.createSingleRoom();
        Room room = roomCache.get(roomId);
        // 2、为每个用户创建一个会话
        contactService.createFriendContact(room.getId(),uid1, uid2);
        // 3、创建一个好友房间
        RoomFriend roomFriend = RoomFriend.builder()
                .roomId(roomId)
                .uid1(Math.min(uid1, uid2))
                .uid2(Math.max(uid1, uid2))
                .roomKey(getRoomKey(uid1, uid2))
                .build();
        roomFriendDao.save(roomFriend);
        return roomId;
    }

    @Override
    public RoomFriend getByUids(Long uid1, Long uid2) {
        return roomFriendDao.getByUids(Math.min(uid1, uid2), Math.max(uid1, uid2));
    }

    private String getRoomKey(Long uid1, Long uid2) {
        return Math.min(uid1, uid2) + "_" + Math.max(uid1, uid2);
    }
}
