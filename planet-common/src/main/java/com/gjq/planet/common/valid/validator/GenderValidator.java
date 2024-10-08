package com.gjq.planet.common.valid.validator;

import com.gjq.planet.common.enums.blog.GenderEnum;
import com.gjq.planet.common.valid.Gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/29 17:41
 * @description: 性别字段校验器
 */
public class GenderValidator implements ConstraintValidator<Gender,Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        GenderEnum genderEnum = GenderEnum.of(integer);
        return Objects.nonNull(genderEnum);
    }
}
