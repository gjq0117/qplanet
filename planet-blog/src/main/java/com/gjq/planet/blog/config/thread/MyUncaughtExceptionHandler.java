package com.gjq.planet.blog.config.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: gjq0117
 * @date: 2024/4/23 12:05
 * @description:
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("111Exception in thead", e);
    }
}
