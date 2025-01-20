package com.gmf.user_management.modules.sapLoginType.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Data;

@Data
public class SapLoginTypeRequest {
    @Sanitizer
    private String filterSapLoginType;
}
