package com.gmf.user_management.core.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoHtmlValidator.class)
@Documented
public @interface Sanitizer {
    // TODO use a better message, look up ValidationMEssages.properties
    String message() default "Not Allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}