package com.gjq.planet.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/7/6 16:07
 * @description: 分布式锁 注解式
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedissonLock {

    /**
     * key的前缀，默认采用方法全限定名，可以自己指定
     *
     * @return
     */
    String prefixKey() default "";

    /**
     * key
     *
     * @return
     */
    String key();

    /**
     *  等待锁的排队时间  默认立即失败
     *
     * @return
     */
    int waitTime() default -1;

    /**
     * 时间单位 默认毫秒
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
