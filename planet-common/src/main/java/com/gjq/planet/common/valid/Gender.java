package com.gjq.planet.common.valid;

import com.gjq.planet.common.enums.blog.GenderEnum;
import com.gjq.planet.common.valid.validator.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gjq0117
 * @date: 2024/4/29 17:40
 * @description: 自定义注解，用于校验标注的字段的值是否在本系统【性别】的定义中
 * @see GenderEnum
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {

    String message() default "性别不符合系统规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
