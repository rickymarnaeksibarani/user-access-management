package com.gmf.user_management.modules.applicationLicense.dto;

import com.gmf.user_management.core.enums.LicenseCategory;
import com.gmf.user_management.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLicenseResponDTO {
    private Long idApplicationLicense;
    private String applicationName;
    private String licenseType;
    private Integer quantity;
    private LicenseCategory licenseCategory;
    private Date expiredDate;
    private Status activeStatus;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
