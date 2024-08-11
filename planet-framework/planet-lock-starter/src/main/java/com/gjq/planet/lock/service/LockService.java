package com.gjq.planet.lock.service;

import com.gjq.planet.common.exception.BusinessException;
import com.gjq.planet.common.exception.CommonErrorEnum;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/7/6 16:09
 * @description: 分布式锁 业务层
 */
public class LockService {

    @Autowired
    private RedissonClient redissonClient;

    @SneakyThrows
    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(waitTime, timeUnit);
        if (!success) {
            // 加锁失败
            throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
        }
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    @FunctionalInterface
    public interface Supplier<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }
}
