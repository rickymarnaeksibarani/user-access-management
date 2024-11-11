package com.gmf.user_management.masterData.applicationLicense.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Data;

@Data
public class ApplicationLicenseRequest {
    @Sanitizer
    private String searchTerm;
}
