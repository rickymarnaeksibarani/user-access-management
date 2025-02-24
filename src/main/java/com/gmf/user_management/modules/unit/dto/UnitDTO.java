package com.gmf.user_management.modules.unit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    @JsonProperty("businessUnitCodeList")
    @NotEmpty
    private List<Long> businessUnitCodeList;

    @JsonProperty("unit")
    @NotEmpty
    private String unit;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
