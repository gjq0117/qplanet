package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.GroupMemberDao;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.blog.dao.RoomGroupDao;
import com.gjq.planet.blog.service.IGroupMemberService;
import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.common.domain.entity.Contact;
import com.gjq.planet.common.domain.entity.GroupMember;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.RoomGroup;
import com.gjq.planet.common.domain.vo.req.groupmember.GroupMemberReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.enums.im.ChatGroupRoleEnum;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/24 20:33
 * @description:
 */
@Service
@Slf4j
public class GroupMemberServiceImpl implements IGroupMemberService {

    @Autowired
    private IUserService userService;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private GroupMemberCache groupMemberCache;

    @Autowired
    private GroupMemberDao groupMemberDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomGroupDao roomGroupDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private IRoomService roomService;

    @Override
    public CursorPageBaseResp<GroupMemberResp> getGroupMemberPage(GroupMemberReq req) {
        Room room = roomCache.get(req.getRoomId());
        AssertUtil.isNotEmpty(room, "房间号不存在");
        if (room.isFriendRoom()) {
            // 好友房间 不需要进行下面操作
            return null;
        }
        RoomGroup roomGroup = roomGroupCache.get(room.getId());
         List<Long> uidList = groupMemberCache.getBatch(roomGroup.getId(), null).stream().map(GroupMember::getUid).collect(Collectors.toList());
//        List<Long> uidList = groupMemberDao.ListByGroupId(roomGroup.getId()).stream().map(GroupMember::getUid).collect(Collectors.toList());
        // 排除自己
        uidList.remove(RequestHolder.get().getUid());
        return userService.getUserCursorPage(uidList, req);
    }

    @Override
    public void joinSystemGroup(Long uid) {
        List<Room> allStaffRoomList = roomDao.getAllStaffRoom();
        RoomGroup roomGroup;
        if (Objects.isNull(allStaffRoomList) || allStaffRoomList.isEmpty()) {
            // 初始化全员群聊房间
            roomGroup = initAllStaffRoom();
        } else {
            // 这边先写死只有第一个，之后再扩展
            roomGroup = roomGroupDao.getByRoomId(allStaffRoomList.get(0).getId());
        }
        // 群成员
        GroupMember groupMember = GroupMember.builder()
                .groupId(roomGroup.getId())
                .uid(uid)
                // 默认普通成员
                .role(ChatGroupRoleEnum.COMMON_MEMBER.getType())
                .build();
        groupMemberDao.save(groupMember);
        groupMemberCache.put(groupMember.getGroupId(), groupMember);
        // 会话列表
        Contact contact = Contact.builder()
                .uid(uid)
                .roomId(roomGroup.getRoomId())
                .readTime(new Date())
                .build();
        contactDao.save(contact);
        // 新成员进群
        roomService.newMemberJoining(uid, roomGroup.getRoomId());
    }

    /**
     * 初始化全员群聊房间
     *
     * @return room
     */
    private RoomGroup initAllStaffRoom() {
        // 房间信息
        Room room = Room.builder()
                .type(RoomTypeEnum.ALL_STAFF_ROOM_CHAT.getType())
                .hotFlag(YesOrNoEnum.YES.getCode())
                .build();
        roomDao.save(room);
        // 群组信息
        RoomGroup group = RoomGroup.builder()
                .roomId(room.getId())
                .name("Q星球全员群")
                .avatar("https://minio.qplanet.cn/planet/user/3ea6beec64369c2642b92c6726f1epng.png")
                .build();
        roomGroupDao.save(group);
        return group;
    }
}
