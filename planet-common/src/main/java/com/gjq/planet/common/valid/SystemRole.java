package com.gjq.planet.common.valid;

import com.gjq.planet.common.valid.validator.SystemRoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:21
 * @description: 用于校验标注的字段的值是否在本系统【系统角色】的定义中
 * @see com.gjq.planet.common.enums.SystemRoleEnum
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SystemRoleValidator.class)
public @interface SystemRole {

    String message() default "用户类型不符合系统规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
