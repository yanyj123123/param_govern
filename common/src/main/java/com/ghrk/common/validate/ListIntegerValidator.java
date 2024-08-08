package com.ghrk.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListIntegerValidator implements ConstraintValidator<ValidAuthIdList, List<Integer>> {
    private int min;
    private int max;

    @Override
    public void initialize(ValidAuthIdList constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext context) {

        for (int num : value) {
            if (num < min || num > max) {
                return false; // 列表中有不符合要求的值
            }
        }
        return true; // 列表符合要求
    }
}

