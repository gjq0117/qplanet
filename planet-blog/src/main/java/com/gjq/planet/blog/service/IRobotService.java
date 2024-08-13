package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.robot.RobotReq;
import com.gjq.planet.common.domain.vo.resp.robot.RobotResp;

import java.util.List;

/**
 * <p>
 * 机器人信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
public interface IRobotService {

    /**
     *  新增机器人信息
     *
     * @param req req
     */
    void add(RobotReq req);

    /**
     *  更新机器人信息
     *
     * @param req req
     */
    void update(RobotReq req);

    /**
     *  获取机器人信息列表
     *
     * @return List<RobotResp>
     */
    List<RobotResp> getRobotList();

    /**
     *  通过Id删除指定机器人信息
     *
     * @param robotId
     */
    void deleteById(Long robotId);
}
