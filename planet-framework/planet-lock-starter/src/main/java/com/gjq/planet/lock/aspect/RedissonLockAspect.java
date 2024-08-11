package com.gjq.planet.lock.aspect;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gjq.planet.common.utils.SpElUtils;
import com.gjq.planet.lock.annotation.RedissonLock;
import com.gjq.planet.lock.service.LockService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author: gjq0117
 * @date: 2024/7/6 16:26
 * @description: 分布式锁切面
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // 确保比事务的注解先执行，分布式锁在事务外
public class RedissonLockAspect {

    @Autowired
    private LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 锁前缀
        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
