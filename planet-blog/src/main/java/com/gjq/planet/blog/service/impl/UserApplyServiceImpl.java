package com.gjq.planet.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gjq.planet.blog.dao.UserApplyDao;
import com.gjq.planet.blog.dao.UserFriendDao;
import com.gjq.planet.blog.service.*;
import com.gjq.planet.blog.service.adapter.MessageBuilder;
import com.gjq.planet.blog.service.adapter.UserApplyBuilder;
import com.gjq.planet.blog.service.adapter.UserFriendBuilder;
import com.gjq.planet.common.domain.entity.RoomFriend;
import com.gjq.planet.common.domain.entity.UserApply;
import com.gjq.planet.common.domain.entity.UserFriend;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.req.userapply.FriendApplyReq;
import com.gjq.planet.common.domain.vo.req.userapply.UserApplyPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.userapply.FriendApplyResp;
import com.gjq.planet.common.enums.im.ApplyStatusEnum;
import com.gjq.planet.common.enums.im.UserApplyTypeEnum;
import com.gjq.planet.common.exception.BusinessException;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/18 17:43
 * @description:
 */
@Service
@Slf4j
public class UserApplyServiceImpl implements IUserApplyService {

    @Autowired
    private UserApplyDao userApplyDao;

    @Autowired
    private UserFriendDao userFriendDao;

    @Autowired
    private IUserFriendService userFriendService;

    @Autowired
    private IRoomFriendService roomFriendService;

    @Autowired
    private IContactService contactService;

    @Autowired
    private IMessageService chatService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void friendApply(FriendApplyReq req) {
        if (ObjectUtil.notEqual(RequestHolder.get().getUid(), req.getUid())) {
            throw new BusinessException("申请人与本人身份不符 uid:" + RequestHolder.get().getUid() + "targetId:" + req.getUid());
        }
        // 判断是不是双向好友
        AssertUtil.isFalse(userFriendService.isFriend(req.getUid(), req.getTargetId()), "你们已经是好友啦!");
        // 判断有没有申请过且不是已拒绝状态的申请信息(你向TA申请的)
        UserApply applyRecord = userApplyDao.getUnReviewApplyRecord(req.getUid(), req.getType(), req.getTargetId());
        AssertUtil.isEmpty(applyRecord, "你已经向TA申请过好友啦，不要重复申请噢！");
        // 如果对方已经向你发起过一个还未处理好友申请，你再向TA申请好友，则默认同意好友(TA向你申请的)
        applyRecord = userApplyDao.getUnReviewApplyRecord(req.getTargetId(), req.getType(), req.getUid());
        if (Objects.nonNull(applyRecord)) {
            // 同意好友申请
            this.agreeFriendApply(req.getUid(), req.getTargetId());
        }
        UserApply userApply = UserApplyBuilder.buildFriendApply(req);
        userApplyDao.save(userApply);
        // TODO websocket推送消息给指定用户
    }

    @Override
    public CursorPageBaseResp<FriendApplyResp> pageFriendApplyList(UserApplyPageReq req) {
        Long uid = RequestHolder.get().getUid();
        if (ObjectUtil.notEqual(uid, req.getCurrUid())) {
            throw new BusinessException("操作用户与当前用户不一致，crrUid：" + uid + "操作uid：" + req.getCurrUid());
        }
        CursorPageBaseResp<UserApply> result = userApplyDao.pageFriendApplyByTargetId(req);
        List<FriendApplyResp> friendApplyRespList = UserApplyBuilder.buildFriendApplyResp(result.getList());
        return new CursorPageBaseResp<>(result.getCursor(), result.getIsLast(), friendApplyRespList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void agreeFriendApply(Long currUid, Long targetId) {
        //1、检验是否有这个申请(TA向你申请的)，且申请必须要是待审核状态
        checkUserApplyIsAvailable(targetId, currUid, UserApplyTypeEnum.FRIEND_APPLY.getType());
        //2、改变申请记录状态(TA向你申请的)
        userApplyDao.changeApplyStatus(currUid, UserApplyTypeEnum.FRIEND_APPLY.getType(), targetId, ApplyStatusEnum.AGREED.getStatus());
        //3、向数据库中插入两条好友记录（双向好友关系）
        UserFriend userFriend1 = UserFriendBuilder.buildUserFriend(currUid, targetId);
        userFriendDao.save(userFriend1);
        UserFriend userFriend2 = UserFriendBuilder.buildUserFriend(targetId, currUid);
        userFriendDao.save(userFriend2);
        //4、如果没有就创建好友房间/会话（如果之前是好友，然后删了，会有房间记录）
        Long roomId;
        RoomFriend roomFriend = roomFriendService.getByUids(currUid, targetId);
        if (Objects.nonNull(roomFriend)) {
            roomId = roomFriend.getRoomId();
        } else {
            roomId = roomFriendService.createFriendRoom(currUid, targetId);
        }
        //5、向双方推送一个新会话
        contactService.pushNewContact(roomId, new HashSet<>(Arrays.asList(currUid, targetId)));
        //6、同意方（currUid）向房间内发送一条消息
        ChatMessageReq req = MessageBuilder.buildHelloMsgReq(roomId);
        chatService.sendMsg(currUid, req);
    }


    @Override
    public void rejectFriendApply(Long currUid, Long targetId) {
        // 校验申请记录
        checkUserApplyIsAvailable(targetId, currUid, UserApplyTypeEnum.FRIEND_APPLY.getType());
        // 改变申请记录状态
        userApplyDao.changeApplyStatus(currUid, UserApplyTypeEnum.FRIEND_APPLY.getType(), targetId, ApplyStatusEnum.REJECTED.getStatus());
    }

    /**
     * 检查用户申请是否可用（即待审核状态）
     *
     * @param applyUid  申请用户ID
     * @param targetId  目标用户ID
     * @param applyType 申请类型
     */
    private void checkUserApplyIsAvailable(Long applyUid, Long targetId, Integer applyType) {
        UserApply unRejectApplyRecord = userApplyDao.getUnReviewApplyRecord(applyUid, applyType, targetId);
        AssertUtil.isNotEmpty(unRejectApplyRecord, "此待审核的申请记录不存在");
    }
}
