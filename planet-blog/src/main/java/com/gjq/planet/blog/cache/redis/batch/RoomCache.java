package com.gjq.planet.blog.cache.redis.batch;

import cn.hutool.core.collection.CollectionUtil;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.common.constant.IMRedisKey;
import com.gjq.planet.common.domain.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/24 21:01
 * @description:
 */
@Component
public class RoomCache extends AbstractRedisHashCache<Room> {

    @Autowired
    private RoomDao roomDao;

    @Override
    protected String getRedisKey() {
        return IMRedisKey.getKey(IMRedisKey.ROOM_INFO);
    }

    @Override
    protected String getRedisKey(Long keyNum) {
        return "";
    }

    @Override
    protected String getHashKey(Room room) {
        return String.valueOf(room.getId());
    }

    @Override
    protected List<Room> load(List<Long> keys) {
        return CollectionUtil.isEmpty(keys) ? roomDao.list() : roomDao.listByIds(keys);
    }

    @Override
    protected List<Room> load(Long keyNum, List<Long> keys) {
        return Collections.emptyList();
    }

    @Override
    protected Long getExpireSeconds() {
        return 60 * 60 * 24L;
    }
}
