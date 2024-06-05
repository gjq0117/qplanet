package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.entity.RoomFriend;

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
     */
    Long createFriendRoom(Long uid1, Long uid2);

    /**
     *  通过uid获取好友房间
     *
     * @param uid1
     * @param uid2
     * @return
     */
    RoomFriend getByUids(Long uid1, Long uid2);
}
