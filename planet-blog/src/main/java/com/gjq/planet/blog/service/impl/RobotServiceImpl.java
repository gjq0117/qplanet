package com.gjq.planet.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gjq.planet.blog.config.openai.OpenAiFactory;
import com.gjq.planet.blog.dao.RobotDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.*;
import com.gjq.planet.blog.service.adapter.MessageBuilder;
import com.gjq.planet.common.domain.entity.Robot;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.req.robot.RobotReq;
import com.gjq.planet.common.domain.vo.resp.robot.RobotResp;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import com.gjq.planet.common.exception.BusinessException;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    private IUserFriendService userFriendService;

    @Autowired
    private IGroupMemberService groupMemberService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IContactService contactService;

    @Autowired
    @Lazy
    private IMessageService chatService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public void add(RobotReq req) {
        //1、插入机器人信息
        Robot insert = new Robot();
        BeanUtils.copyProperties(req, insert);
        robotDao.save(insert);
        //2、插入对应的用户信息
        User robotUser = User.builder()
                .id(insert.getId())
                .nickname(req.getName())
                .avatar(req.getAvatar())
                .userType(SystemRoleEnum.CUSTOMER.getCode())
                .userStatus(YesOrNoEnum.YES.getCode())
                .build();
        userDao.save(robotUser);
        insert.setUid(robotUser.getId());
        robotDao.updateById(insert);

        // 所有用户信息，排除机器人本身
        List<Long> uidList = userDao.list().stream().map(User::getId).filter(uid -> !Objects.equals(uid, robotUser.getId())).collect(Collectors.toList());
        // 增加机器人配置
        OpenAiFactory.register(insert);
        // 初始化机器人聊天环境
        initRobotChatEnvironment(robotUser.getId(), uidList);
    }

    @Override
    public void update(RobotReq req) {
        Robot robot = new Robot();
        BeanUtils.copyProperties(req, robot);
        robotDao.updateById(robot);
        // 更新内存中机器人信息
        if (YesOrNoEnum.NO.getCode().equals(req.getEnabled())) {
            // 停用机器人
            OpenAiFactory.removeModel(req.getId());
        } else {
            // 启用机器人
            OpenAiFactory.register(robot);
        }
    }

    @Override
    public List<RobotResp> getRobotList() {
        return robotDao.list().stream().map(robot -> {
            RobotResp robotResp = new RobotResp();
            // 组装用户信息
            User user = userDao.getById(robot.getUid());
            robotResp.setName(user.getNickname());
            robotResp.setAvatar(user.getAvatar());

            // 组装ai信息
            BeanUtils.copyProperties(robot, robotResp);

            // 总调用次数 - 总失败次数
            BigDecimal totalSuccess = BigDecimal.valueOf(Optional.ofNullable(robot.getTotalReplyNum()).orElse(0) - Optional.ofNullable(robot.getTotalFailNum()).orElse(0));
            // 总调用次数
            Integer total = Optional.ofNullable(robot.getTotalReplyNum()).orElse(0);
            // 计算响应成功率【(总调用次数 - 总失败次数) / 总调用次数】
            if (BigDecimal.ZERO.equals(totalSuccess)) {
                robotResp.setSuccessRate(0f);
            } else {
                robotResp.setSuccessRate(totalSuccess.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP).floatValue() * 100);
            }
            // 今日剩余次数【每日上线次数 - 今日成功次数】
            robotResp.setTotalResidue(Optional.ofNullable(robotResp.getDailyLimitNum()).orElse(0) - totalSuccess.intValue());

            return robotResp;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long robotId) {
        robotDao.removeById(robotId);
        // 删除内存中的机器人信息
        OpenAiFactory.removeModel(robotId);
        // 删除用户
        userDao.removeById(robotId);
        // 离开系统群聊
        groupMemberService.leaveSystemGroup(robotId);
        // 删除好友、房间等信息
        roomService.clearAllRoomAndFriend(robotId);
    }

    @Override
    public String call(Long robotId, String content) {
        if (Objects.isNull(OpenAiFactory.getModel(robotId))) {
            throw new BusinessException("此机器人不存在！！！");
        }
        callBeforePostProcess(robotId);
        return tryCall(robotId, content);
    }

    private String tryCall(Long robotId ,String msg) {
        OpenAiChatModel model = OpenAiFactory.getModel(robotId);
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return model.call(msg);
            }
        });
        try {
            // 等待5s
            String reply = future.get(10, TimeUnit.SECONDS);
            if (StringUtils.isNotBlank(reply)) {
                // 响应成功
                return reply;
            }
        } catch (Exception e) {
            return handleFail(robotId);
        }
        // 响应失败
        return handleFail(robotId);
    }


    /**
     *  处理机器人响应失败
     *
     * @param robotId 机器人ID
     * @return
     */
    private String handleFail(Long robotId) {
        // 今日失败次数、总失败次数 + 1
        Robot robot = robotDao.getById(robotId);
        robot.setTodayFailNum(Optional.ofNullable(robot.getTodayFailNum()).orElse(0) + 1);
        robot.setTotalFailNum(Optional.ofNullable(robot.getTotalFailNum()).orElse(0) + 1);
        robotDao.updateById(robot);
        return "抱歉，脑袋宕机辣~";
    }

    /**
     *  调用前置处理器
     *
     * @param robotId 机器人ID
     * @return
     */
    private void callBeforePostProcess(Long robotId) {
        // 总调用次数、今日调用次数 + 1
        Robot robot = robotDao.getById(robotId);
        robot.setTodayReplyNum(Optional.ofNullable(robot.getTodayReplyNum()).orElse(0) + 1);
        robot.setTotalReplyNum(Optional.ofNullable(robot.getTotalReplyNum()).orElse(0) + 1);
        robotDao.updateById(robot);
    }


    /**
     * 为指定用户初始化机器人聊天环境，包括房间、会话、好友关系
     *
     * @param robotUid 机器人对应的用户ID
     */
    private void initRobotChatEnvironment(Long robotUid, List<Long> uidList) {
        //1、机器人加入大群聊
        groupMemberService.joinSystemGroup(robotUid);
        for (Long uid : uidList) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 为每个用户添加这个机器人好友
                    if (uid.equals(robotUid)) return;
                    Long roomId = userFriendService.initSingleChatEnvironment(uid, robotUid, RoomTypeEnum.ROBOT_CHAT);
                    // 双方推送一个新的会话消息
                    contactService.pushNewContact(roomId, new HashSet<>(Arrays.asList(uid, robotUid)));
                    // 机器人主动给对方发送一条招呼语句
                    ChatMessageReq req = MessageBuilder.buildTextMsgReq(roomId, "您好，尊贵的Q星球会员！我是您的新助理，之后有什么问题敬请问我吧~", null);
                    chatService.sendMsg(robotUid, req);
                }
            });
        }

    }
}
