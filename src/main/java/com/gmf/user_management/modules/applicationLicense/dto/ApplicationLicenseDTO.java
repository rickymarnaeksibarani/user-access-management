package com.gmf.user_management.modules.applicationLicense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.LicenseCategory;
import com.gmf.user_management.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLicenseDTO {
    @JsonProperty("applicationName")
    private String applicationName;
    @JsonProperty("licenseType")
    private String licenseType;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("licenseCategory")
    private LicenseCategory licenseCategory;
    @JsonProperty("expiredDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiredDate;
    @JsonProperty("activeStatus")
    private Status activeStatus;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private String updatedBy;
}
