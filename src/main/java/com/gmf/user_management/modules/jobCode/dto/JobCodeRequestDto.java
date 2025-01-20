package com.gmf.user_management.modules.jobCode.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobCodeRequestDto {
    @Sanitizer
    private String searchJobCode;
}
