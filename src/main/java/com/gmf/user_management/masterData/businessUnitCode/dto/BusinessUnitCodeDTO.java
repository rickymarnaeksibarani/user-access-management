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
    @JsonProperty("id_business_unit_code")
    private Long id_business_unit_code;

    @JsonProperty("business_unit_code")
    private String business_unit_code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dinas")
    private String dinas;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("created_by")
    private String created_by;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

    @JsonProperty("updated_by")
    private String updated_by;

}
