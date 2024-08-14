package com.gjq.planet.blog;


import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


/**
 * @author: gjq0117
 * @date: 2024/4/13 13:06
 * @description: 原神！启动
 */
@SpringBootApplication(exclude = {OpenAiAutoConfiguration.class})
@MapperScan({"com.gjq.planet.blog.mapper"})
@Import({RocketMQAutoConfiguration.class})
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
