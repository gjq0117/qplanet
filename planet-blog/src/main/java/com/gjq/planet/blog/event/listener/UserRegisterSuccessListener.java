package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.dao.ContactDao;
import com.gjq.planet.blog.dao.GroupMemberDao;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.blog.dao.RoomGroupDao;
import com.gjq.planet.blog.event.UserRegisterSuccessEvent;
import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.enums.im.ChatGroupRoleEnum;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import com.gjq.planet.common.utils.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/29 11:38
 * @description: 用户注册成功事件监听器
 */
@Component
public class UserRegisterSuccessListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomGroupDao roomGroupDao;

    @Autowired
    private GroupMemberDao groupMemberDao;

    @Autowired
    private GroupMemberCache groupMemberCache;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private IRoomService roomService;


    /**
     * 初始化IP信息
     */
    @TransactionalEventListener(classes = UserRegisterSuccessEvent.class, fallbackExecution = true, phase = TransactionPhase.BEFORE_COMMIT)
    public void initIpInfo(UserRegisterSuccessEvent userRegisterSuccessEvent) {
        User user = userRegisterSuccessEvent.getUser();
        ipService.refreshIpInfoAsync(user.getId(), RequestHolder.get().getIp());
    }

    /**
     * 加入大群聊
     */
    @TransactionalEventListener(classes = UserRegisterSuccessEvent.class, fallbackExecution = true, phase = TransactionPhase.BEFORE_COMMIT)
    public void intoGroup(UserRegisterSuccessEvent userRegisterSuccessEvent) {
        User user = userRegisterSuccessEvent.getUser();
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
                .uid(user.getId())
                // 默认普通成员
                .role(ChatGroupRoleEnum.COMMON_MEMBER.getType())
                .build();
        groupMemberDao.save(groupMember);
        groupMemberCache.put(groupMember.getGroupId(), groupMember);
        // 会话列表
        Contact contact = Contact.builder()
                .uid(user.getId())
                .roomId(roomGroup.getRoomId())
                .readTime(new Date())
                .build();
        contactDao.save(contact);
        // 新成员进群
        roomService.newMemberJoining(user.getId(), roomGroup.getRoomId());
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
