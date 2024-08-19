package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.blog.dao.RoomFriendDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.resp.websocket.base.NewMemberJoin;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/29 16:06
 * @description: 房间业务
 */
@Service
@Slf4j
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private GroupMemberCache groupMemberCache;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Autowired
    private WebsocketService websocketService;

    @Override
    public Long getOnlineNum(Long roomId) {
        Room room = roomCache.get(roomId);
        if (room.isFriendRoom() || room.isRobotRoom()) {
            // 如果是单聊房间或者机器人房间直接返回0
            return 0L;
        }
        // TODO 优化
        RoomGroup roomGroup = roomGroupCache.get(roomId);
        List<GroupMember> memberCache = groupMemberCache.getBatch(roomGroup.getId(), null);
        List<Long> uidList = memberCache.stream().map(GroupMember::getUid).collect(Collectors.toList());
        return userDao.listByIds(uidList).stream().filter(user -> YesOrNoEnum.YES.getCode().equals(user.getIsActive())).count();
    }

    @Override
    public Long createSingleRoom(RoomTypeEnum roomTypeEnum) {
        Room room = Room.builder()
                .type(roomTypeEnum.getType())
                .hotFlag(0)
                .build();
        roomDao.save(room);
        return room.getId();
    }

    @Override
    public void newMemberJoining(Long uid, Long roomId) {
        RoomGroup roomGroup = roomGroupCache.get(roomId);
        Set<Long> uidSet = groupMemberCache.getBatch(roomGroup.getId(), null).stream().map(GroupMember::getUid).collect(Collectors.toSet());
        websocketService.pushMsg(new NewMemberJoin(uid), uidSet);
    }

    @Override
    public void clearAllRoomAndFriend(Long uid) {
        // 获取除自己以外的所有用户的uid
        Set<Long> uidList = userDao.list().stream().map(User::getId).filter(id -> !Objects.equals(id, uid)).collect(Collectors.toSet());
        for (Long id :uidList) {
            // 删除好友房间
            RoomFriend roomFriend = roomFriendDao.getByUids(Math.min(id, uid), Math.max(id, uid));
            if (Objects.nonNull(roomFriend)) {
                roomFriendDao.removeById(roomFriend);
                // 删除房间信息
                roomCache.remove(roomFriend.getRoomId());
                roomDao.removeById(roomFriend.getRoomId());
            }
        }
    }
}
