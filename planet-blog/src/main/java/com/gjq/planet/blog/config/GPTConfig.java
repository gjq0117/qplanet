package com.gjq.planet.blog.config;

import com.gjq.planet.blog.dao.RobotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @ClassDescription: gpt配置，注入系统中的机器人信息
 * @Author: gjq
 * @Created: 2024-08-13 16:59
 */
@Component
public class GPTConfig {

    @Autowired
    private RobotDao robotDao;

    @PostConstruct
    private void init() {

    }
}
