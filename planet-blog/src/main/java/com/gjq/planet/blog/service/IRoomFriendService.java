package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.entity.RoomFriend;
import com.gjq.planet.common.enums.im.RoomTypeEnum;

/**
 * <p>
 * 好友房间 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface IRoomFriendService {

    /**
     *  创建好友房间
     *
     * @param uid1
     * @param uid2
     * @param roomTypeEnum 房间类型（一般为好友房间或者机器人房间）
     */
    Long createFriendRoom(Long uid1, Long uid2, RoomTypeEnum roomTypeEnum);

    /**
     *  通过uid获取好友房间
     *
     * @param uid1
     * @param uid2
     * @return
     */
    RoomFriend getByUids(Long uid1, Long uid2);
}
