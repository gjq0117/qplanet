package com.gjq.planet.blog.service;

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
}
