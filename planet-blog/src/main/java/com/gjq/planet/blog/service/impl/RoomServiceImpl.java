package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.common.domain.entity.GroupMember;
import com.gjq.planet.common.domain.entity.RoomGroup;
import com.gjq.planet.common.enums.blog.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private UserDao userDao;

    @Override
    public Long getOnlineNum(Long roomId) {
        RoomGroup roomGroup = roomGroupCache.get(roomId);
        List<GroupMember> memberCache = groupMemberCache.getBatch(roomGroup.getId(), null);
        List<Long> uidList = memberCache.stream().map(GroupMember::getUid).collect(Collectors.toList());
        return userDao.listByIds(uidList).stream().filter(user -> YesOrNoEnum.YES.getCode().equals(user.getIsActive())).count();
    }
}
