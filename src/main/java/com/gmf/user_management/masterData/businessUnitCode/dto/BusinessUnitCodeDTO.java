package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnitCodeDTO {
    @JsonProperty("businessUnitCode")
    private String businessUnitCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dinas")
    private String dinas;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

}
