package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.RoomFriendMapper;
import com.gjq.planet.common.domain.entity.RoomFriend;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好友房间 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {

    public RoomFriend getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(RoomFriend::getRoomId, roomId)
                .one();
    }

    public RoomFriend getByUids(long min, long max) {
        return lambdaQuery()
                .eq(RoomFriend::getUid1, min)
                .eq(RoomFriend::getUid2, max)
                .one();
    }
}
