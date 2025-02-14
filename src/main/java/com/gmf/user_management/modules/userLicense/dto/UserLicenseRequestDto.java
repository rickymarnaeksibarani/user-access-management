package com.gmf.user_management.modules.userLicense.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLicenseRequestDto {
    private String filterByApplicationName;
    private String filterByPersonalName;
    private String filterByDinas;
    private String filterByPartner;
    private String filterByUnit;
    private String filterByPersonalNumber;
    private String filterByPassCardNumber;
    private Long applicationId;

}
