package com.gmf.user_management.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsNumericValidator.class)
public @interface IsNumeric {

    public String message() default "Input should be a numeric";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
