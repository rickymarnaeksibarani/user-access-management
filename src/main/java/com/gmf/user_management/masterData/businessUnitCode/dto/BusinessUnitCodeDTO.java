package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnitCodeDTO {
    @JsonProperty("businessUnitCode")
    private String businessUnitCode;

    @JsonIgnore
    private String partnerName;

    @JsonProperty("partnerExternal")
    private Long partnerExternal;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dinas")
    private String dinas;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

}
