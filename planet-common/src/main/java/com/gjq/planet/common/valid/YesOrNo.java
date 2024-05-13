package com.gjq.planet.common.valid;

import com.gjq.planet.common.valid.validator.YesOrNoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:27
 * @description: 自定义注解，用于校验标注的字段的值是否在本系统【是/否】的定义中
 * @see com.gjq.planet.common.enums.YesOrNoEnum
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YesOrNoValidator.class)
public @interface YesOrNo {

    String message() default "类型不符合系统规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
