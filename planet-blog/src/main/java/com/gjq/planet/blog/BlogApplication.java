package com.gjq.planet.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: gjq0117
 * @date: 2024/4/13 13:06
 * @description: 原神！启动
 */
@SpringBootApplication
@MapperScan({"com.gjq.planet.blog.mapper"})
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
