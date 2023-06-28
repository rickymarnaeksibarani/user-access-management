package com.gmf.user_management.core.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNumericValidator implements ConstraintValidator<IsNumeric, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^[0-9]*$");
    }
}
