package com.gjq.planet.blog.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/4/22 17:16
 * @description: 自定义线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new MyThreadPoolExecutor(
                // 当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行
                Runtime.getRuntime().availableProcessors(),
                100,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000),
                Executors.defaultThreadFactory(),
                //满了调用线程执行，认为重要任务
                new ThreadPoolExecutor.CallerRunsPolicy(),
                // 线程池名字前缀
                "planet-executor-"
        );
    }

}
