package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.service.IGroupMemberService;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.common.domain.entity.GroupMember;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.RoomGroup;
import com.gjq.planet.common.domain.vo.req.groupmember.GroupMemberReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        // 排除自己
        uidList.remove(RequestHolder.get().getUid());
        return userService.getUserCursorPage(uidList, req);
    }
}
