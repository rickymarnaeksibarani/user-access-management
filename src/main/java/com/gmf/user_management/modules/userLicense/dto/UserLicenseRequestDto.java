package com.gmf.user_management.modules.userLicense.dto;

import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserLicenseRequestDto {
    private String filterByApplicationName;
    private String filterByPersonalName;
    private String filterByDinas;
    private String filterByPartner;
}
