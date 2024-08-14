package com.gjq.planet.common.valid.validator;

import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.valid.YesOrNo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:31
 * @description:
 */
public class YesOrNoValidator implements ConstraintValidator<YesOrNo, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.of(integer);
        return Objects.nonNull(yesOrNoEnum);
    }
}
