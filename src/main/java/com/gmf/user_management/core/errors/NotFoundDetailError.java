package com.gmf.user_management.core.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotFoundDetailError extends AppSubError {

    private final Map<String, String> detail = new HashMap<>();

    public NotFoundDetailError(String key, String value) {
        this.detail.put(key, value);
    }
}
