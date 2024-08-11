package com.gjq.planet.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.dao.UserFriendDao;
import com.gjq.planet.blog.service.IRoomFriendService;
import com.gjq.planet.blog.service.IUserFriendService;
import com.gjq.planet.blog.service.adapter.GroupMemberBuilder;
import com.gjq.planet.blog.service.adapter.UserFriendBuilder;
import com.gjq.planet.common.domain.entity.RoomFriend;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.entity.UserFriend;
import com.gjq.planet.common.domain.vo.req.userfriend.PutFriendRemarkReq;
import com.gjq.planet.common.domain.vo.req.userfriend.UserFriendPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.domain.vo.resp.userfriend.FriendDetailedResp;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.exception.BusinessException;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/18 17:43
 * @description:
 */
@Service
@Slf4j
public class UserFriendServiceImpl implements IUserFriendService {

    @Autowired
    private UserFriendDao userFriendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private IRoomFriendService roomFriendService;

    @Override
    public Boolean isFriend(Long currUid, Long uid) {
        if (Objects.isNull(currUid) || Objects.isNull(uid)) {
            return false;
        }
        if (currUid.equals(uid)) {
            // 本人
            return true;
        }
        // 必须要是双向好友
        UserFriend userFriend1 = userFriendDao.getByUidAndFId(currUid, uid);
        UserFriend userFriend2 = userFriendDao.getByUidAndFId(uid, currUid);
        return Objects.nonNull(userFriend1) && Objects.nonNull(userFriend2);
    }

    @Override
    public CursorPageBaseResp<GroupMemberResp> pageFriendInfo(UserFriendPageReq req) {
        return pageFriendBaseInfo(req, userFriendList ->
                // 无需过滤，查找所有好友信息
                userFriendList
        );
    }

    @Override
    public CursorPageBaseResp<GroupMemberResp> pageCareFriendInfo(UserFriendPageReq req) {
        return pageFriendBaseInfo(req, userFriendList ->
                userFriendList.stream()
                        // 特别关心
                        .filter(userFriend -> YesOrNoEnum.YES.getCode().equals(userFriend.getIsCare()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public FriendDetailedResp getFriendDetailed(Long currUid, Long friendUid) {
        UserFriend userFriend = getUserFriend(currUid, friendUid);
        User friend = userDao.getById(friendUid);
        return UserFriendBuilder.buildFriendDetailed(userFriend, friend);
    }

    @Override
    public void putFriendRemark(Long currUid, PutFriendRemarkReq req) {
        getUserFriend(currUid, req.getUid());
        userFriendDao.putFriendRemark(currUid, req.getUid(), req.getRemark());
    }

    @Override
    public void putFriendToCare(Long currUid, Long friendUid) {
        getUserFriend(currUid, friendUid);
        userFriendDao.changeFriendCare(currUid, friendUid, YesOrNoEnum.YES.getCode());
    }

    @Override
    public void cancelFriendCare(Long currUid, Long friendUid) {
        getUserFriend(currUid, friendUid);
        userFriendDao.changeFriendCare(currUid, friendUid, YesOrNoEnum.NO.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFriend(Long currUid, Long friendUid) {
        // 双向好友删除
        UserFriend userFriend1 = getUserFriend(currUid, friendUid);
        UserFriend userFriend2 = getUserFriend(friendUid, currUid);
        userFriendDao.removeById(userFriend1.getId());
        userFriendDao.removeById(userFriend2.getId());
        // 删除房间缓存
        RoomFriend roomFriend = roomFriendService.getByUids(userFriend1.getUid(), userFriend2.getUid());
        roomCache.remove(roomFriend.getRoomId());
    }

    /**
     * 获取好友信息
     *
     * @param currUid   当前用户ID
     * @param friendUid 好友ID
     * @return UserFriend
     */
    private UserFriend getUserFriend(Long currUid, Long friendUid) {
        UserFriend userFriend = userFriendDao.getFriendDetailed(currUid, friendUid);
        AssertUtil.isNotEmpty(userFriend, "你们不是好友关系噢~");
        return userFriend;
    }

    private CursorPageBaseResp<GroupMemberResp> pageFriendBaseInfo(UserFriendPageReq req, SFunction<List<UserFriend>, List<UserFriend>> sFunction) {
        // 验证操作用户是否与系统用户一致
        if (ObjectUtil.notEqual(req.getCurrUid(), RequestHolder.get().getUid())) {
            throw new BusinessException("操作用户与系统用户不一致，systemUid：" + RequestHolder.get().getUid() + "，currUid：" + req.getCurrUid());
        }
        CursorPageBaseResp<UserFriend> userFriendPage = userFriendDao.pageFriendInfo(req);
        if (CollectionUtil.isEmpty(userFriendPage.getList())) {
            return new CursorPageBaseResp<>(userFriendPage.getCursor(), userFriendPage.getIsLast(), new ArrayList<>());
        }
        List<UserFriend> friendUserList = sFunction.apply(userFriendPage.getList());
        if (CollectionUtil.isEmpty(friendUserList)) {
            return new CursorPageBaseResp<>(userFriendPage.getCursor(), userFriendPage.getIsLast(), new ArrayList<>());
        }
        List<User> userList = userDao.listByIds(friendUserList.stream().map(UserFriend::getFriendUid).collect(Collectors.toList()));
        List<GroupMemberResp> groupMemberRespList = GroupMemberBuilder.buildGroupMember(userList);
        return new CursorPageBaseResp<>(userFriendPage.getCursor(), userFriendPage.getIsLast(), groupMemberRespList);
    }

}
