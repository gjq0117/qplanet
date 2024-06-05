package com.gjq.planet.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gjq0117
 * @date: 2024/5/11 15:17
 * @description: 安全执行： 标注在方法上，保证此方法安全执行（前提是此方法必须在事务内）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecureInvoke {

    /**
     * 最大重试次数（默认3次）
     *
     * @return
     */
    int maxRetryTimes() default 3;

    /**
     * 是否开启异步执行（默认开启）
     *
     * @return
     */
    boolean async() default true;
}
