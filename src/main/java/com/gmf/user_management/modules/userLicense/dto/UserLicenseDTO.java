package com.gmf.user_management.modules.userLicense.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLicenseDTO {
    @JsonProperty("applicationLicenseList")
    private List<Long> applicationLicenseList;

    @JsonProperty("personalList")
    private List<Long> personalList;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

}
