package com.gmf.user_management.modules.applicationLicense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.LicenseCategory;
import com.gmf.user_management.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLicenseDTO {
    @JsonProperty("applicationName")
    @NotEmpty
    private String applicationName;

    @JsonProperty("licenseType")
    @NotEmpty
    private String licenseType;

    @JsonProperty("quantity")
    @NotNull
    private Integer quantity;

    @JsonProperty("licenseCategory")
    @NotNull
    private LicenseCategory licenseCategory;

    @JsonProperty("expiredDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    private Date expiredDate;

    @JsonProperty("activeStatus")
    @NotNull
    private Status activeStatus;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
