package com.gjq.planet.common.annotation;

import java.lang.annotation.*;

/**
 * @author: gjq0117
 * @date: 2024/4/23 12:16
 * @description: 一般在controller上加上此注解，表示该请求需要管理员权限
 */

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanetAdmin {
}
