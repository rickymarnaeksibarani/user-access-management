package com.gmf.user_management.core.errors;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AppParameterValidationError extends AppSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public AppParameterValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
