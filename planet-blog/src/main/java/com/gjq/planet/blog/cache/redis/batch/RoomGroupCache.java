package com.gjq.planet.blog.cache.redis.batch;

import com.gjq.planet.blog.dao.RoomGroupDao;
import com.gjq.planet.common.constant.IMRedisKey;
import com.gjq.planet.common.domain.entity.RoomGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/24 21:06
 * @description: 群聊房间信息缓存
 */
@Component
public class RoomGroupCache extends AbstractRedisHashCache<RoomGroup> {

    @Autowired
    private RoomGroupDao roomGroupDao;

    @Override
    protected String getRedisKey() {
        return IMRedisKey.getKey(IMRedisKey.ROOM_GROUP_INFO);
    }

    @Override
    protected String getRedisKey(Long keyNum) {
        return "";
    }

    @Override
    protected String getHashKey(RoomGroup roomGroup) {
        return String.valueOf(roomGroup.getRoomId());
    }

    @Override
    protected List<RoomGroup> load(List<Long> keys) {
        return roomGroupDao.listByRoomIds(keys);
    }

    @Override
    protected List<RoomGroup> load(Long keyNum, List<Long> keys) {
        return Collections.emptyList();
    }

    @Override
    protected Long getExpireSeconds() {
        return 60 * 60 * 24L;
    }
}
