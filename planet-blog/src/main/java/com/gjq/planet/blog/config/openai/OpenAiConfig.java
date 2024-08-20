package com.gjq.planet.blog.config.openai;

import com.gjq.planet.blog.dao.RobotDao;
import com.gjq.planet.common.domain.entity.Robot;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @ClassDescription: openAi配置，注入系统中的机器人信息
 * @Author: gjq
 * @Created: 2024-08-13 16:59
 */
@Configuration
@EnableScheduling
public class OpenAiConfig {

    @Autowired
    private RobotDao robotDao;

    @PostConstruct
    private void init() {
        List<Robot> robotList = robotDao.list();
        robotList.forEach(robot -> {
            // 只加载启用的机器人
            if (YesOrNoEnum.YES.getCode().equals(robot.getEnabled())) {
                OpenAiFactory.register(robot);
            }
        });
    }

    /**
     *  每天凌晨刷新机器人使用次数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshUseTimes() {
        List<Robot> robots = robotDao.list().stream().peek(robot -> {
            robot.setTodayFailNum(0);
            robot.setTodayReplyNum(0);
        }).toList();
        robotDao.updateBatchById(robots);
    }
}
