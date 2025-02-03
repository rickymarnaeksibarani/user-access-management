package com.gmf.user_management.modules.userLicense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> applicationLicenseList;

    @JsonProperty("personalList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> personalList;

    @JsonProperty("createdBy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;

    @JsonProperty("updatedBy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;

}
