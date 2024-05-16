package com.gjq.planet.common.valid.validator;

import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.valid.SystemRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:22
 * @description: 系统角色校验器
 */
public class SystemRoleValidator implements ConstraintValidator<SystemRole, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        SystemRoleEnum systemRoleEnum = SystemRoleEnum.of(integer);
        return Objects.nonNull(systemRoleEnum);
    }
}
