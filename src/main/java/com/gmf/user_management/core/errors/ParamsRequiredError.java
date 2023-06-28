package com.gmf.user_management.core.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ParamsRequiredError extends AppSubError {
    private String object;
    private String message;
    private Object rejectedValue;

    public ParamsRequiredError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
