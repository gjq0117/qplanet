package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.RobotMapper;
import com.gjq.planet.common.domain.entity.Robot;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机器人信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
@Service
public class RobotDao extends ServiceImpl<RobotMapper, Robot> {
}
