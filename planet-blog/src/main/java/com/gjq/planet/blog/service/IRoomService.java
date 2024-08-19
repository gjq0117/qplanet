package com.gjq.planet.blog.service;

import com.gjq.planet.common.enums.im.RoomTypeEnum;

/**
 * <p>
 * 房间信息表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface IRoomService  {

    /**
     *  获取房间在线人数
     *
     * @param roomId 当局号
     * @return 在线人数
     */
    Long getOnlineNum(Long roomId);

    /**
     *  创建单聊房间
     *
     * @param roomTypeEnum 房间类型（一般为单聊房间或者机器人房间）
     * @return 房间号
     */
    Long createSingleRoom(RoomTypeEnum roomTypeEnum);

    /**
     *  新成员进群
     *
     * @param uid 用户ID
     * @param roomId 房间号
     */
    void newMemberJoining(Long uid, Long roomId);

    /**
     *  为指定用户清空所有的好友以及好友房间的信息
     *
     * @param uid
     */
    void clearAllRoomAndFriend(Long uid);
}
