package com.gjq.planet.common.annotation;

import java.lang.annotation.*;

/**
 * @author: gjq0117
 * @date: 2024/4/14 15:00
 * @description: 过滤token，controller方法上加上注解表示此接口访问不需要携带token，此接口是开发接口
 */

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotToken {
}
