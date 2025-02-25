package com.gmf.user_management.modules.unitJobCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitJobCodeDTO {
    @JsonProperty("unitList")
//    @NotEmpty
    private List<Long> unitList;

    @JsonProperty("jobCodeList")
//    @NotEmpty
    private List<Long> jobCodeList;

    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "updatedBy")
    private String updatedBy;
}
