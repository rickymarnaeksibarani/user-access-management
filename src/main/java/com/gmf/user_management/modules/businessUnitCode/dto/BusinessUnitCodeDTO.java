package com.gmf.user_management.modules.businessUnitCode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnitCodeDTO {
    @JsonProperty("businessUnitCode")
    @NotEmpty
    private String businessUnitCode;

    @JsonProperty("partnerExternal")
    @NotNull
    private Long partnerExternal;

    @JsonProperty("description")
    @NotEmpty
    private String description;

    @JsonProperty("dinas")
    @NotEmpty
    private String dinas;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

}
