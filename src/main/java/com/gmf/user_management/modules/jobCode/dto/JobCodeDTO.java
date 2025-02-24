package com.gmf.user_management.modules.jobCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCodeDTO {
    @JsonProperty("jobCode")
    @NotEmpty
    private String jobCode;

    @JsonProperty("jobPosition")
    @NotEmpty
    private String jobPosition;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
