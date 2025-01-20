package com.gmf.user_management.modules.licenseType.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenseTypeRequestDto {
    @Sanitizer
    private String searchTerm;
}
