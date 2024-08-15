package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.dao.*;
import com.gjq.planet.blog.service.IGroupMemberService;
import com.gjq.planet.blog.service.IRobotService;
import com.gjq.planet.common.domain.entity.*;
import com.gjq.planet.common.domain.vo.req.robot.RobotReq;
import com.gjq.planet.common.domain.vo.resp.robot.RobotResp;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 机器人信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
@Service
public class RobotServiceImpl implements IRobotService {

    @Autowired
    private RobotDao robotDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomFriendDao roomFriendDao;

    @Autowired
    private IGroupMemberService groupMemberService;

    @Override
    public void add(RobotReq req) {
        //1、插入机器人信息
        Robot insert = new Robot();
        BeanUtils.copyProperties(req, insert);
        robotDao.save(insert);
        //2、插入对应的用户信息
        User user = User.builder()
                .id(insert.getId())
                .nickname(req.getName())
                .avatar(req.getAvatar())
                .userStatus(YesOrNoEnum.YES.getCode())
                .build();
        userDao.save(user);

        // 所有用户信息
        List<Long> uidList = userDao.list().stream().map(User::getId).collect(Collectors.toList());
        //2、初始化机器人聊天环境
        initRobotChatEnvironment(user.getId(), uidList, user.getId());
    }

    @Override
    public void update(RobotReq req) {
        Robot robot = new Robot();
        BeanUtils.copyProperties(req, robot);
        robotDao.updateById(robot);
    }

    @Override
    public List<RobotResp> getRobotList() {
        return robotDao.list().stream().map(robot -> {
            RobotResp robotResp = new RobotResp();
            BeanUtils.copyProperties(robot, robotResp);
            // 计算响应成功率
            robotResp.setSuccessRate((float) robot.getTotalReplyNum() / (robot.getTotalReplyNum() + robot.getTotalFailNum()));
            return robotResp;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long robotId) {
        robotDao.removeById(robotId);
    }


    /**
     *  为指定用户初始化机器人聊天环境，包括房间、会话、好友关系
     *
     * @param robotId 机器人ID
     */
    private void initRobotChatEnvironment(Long robotUid, List<Long> uidList, Long robotId) {
        //1、机器人加入大群聊
        groupMemberService.joinSystemGroup(robotUid);
        //2、为每个用户都创建一个机器人的房间、会话、好友关系
        for (Long uid : uidList) {
            if (uid.equals(robotUid)) continue;
            // 新增房间
            Room room = Room.builder()
                    .type(RoomTypeEnum.ROBOT_CHAT.getType())
                    .hotFlag(0)
                    .build();
            roomDao.save(room);
            roomCache.put(room);
            // 新增会话
            Contact contact = Contact.builder()
                    .uid(uid)
                    .roomId(room.getId())
                    .build();
            contactDao.save(contact);
            // 好友关系
            RoomFriend roomFriend = RoomFriend.builder()
                    .roomId(room.getId())
                    .uid1(uid)
                    .uid2(robotId)
                    .roomKey(robotId + "_" + uid)
                    .build();
            roomFriendDao.save(roomFriend);
        }

    }


}
