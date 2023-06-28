package com.gmf.user_management.core.validations;

import org.owasp.encoder.Encode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoHtmlValidator implements ConstraintValidator<Sanitizer, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String sanitized = Encode.forHtml(value);
        return sanitized.equals(value);
        // return sanitized.equals(value);
    }
}