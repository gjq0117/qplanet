package com.gjq.planet.blog.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.RoomGroupMapper;
import com.gjq.planet.common.domain.entity.RoomGroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 群聊房间 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {

    /**
     *  通过房间ID获取群组信息
     *
     * @param roomId roomId
     * @return RoomGroup
     */
    public RoomGroup getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(RoomGroup::getRoomId, roomId)
                .one();
    }

    /**
     *  通过房间号列表获取群组信息列表
     *
     * @param keys keys
     * @return List<RoomGroup>
     */
    public List<RoomGroup> listByRoomIds(List<Long> keys) {
        return lambdaQuery()
                .in(!CollectionUtil.isEmpty(keys),RoomGroup::getRoomId,keys)
                .list();
    }
}
