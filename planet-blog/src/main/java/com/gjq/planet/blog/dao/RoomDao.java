package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.RoomMapper;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.enums.im.RoomTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 房间信息表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room>  {

    /**
     *  获取全员群聊
     *
     */
    public List<Room> getAllStaffRoom() {
        return lambdaQuery()
                .eq(Room::getType, RoomTypeEnum.ALL_STAFF_ROOM_CHAT.getType())
                .list();
    }
}
