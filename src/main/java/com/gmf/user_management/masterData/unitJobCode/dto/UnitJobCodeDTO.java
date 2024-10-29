package com.gmf.user_management.masterData.unitJobCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
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
    //table unit
    @JsonProperty("unitList")
    private List<Long> unitList;

    //table jobCode
    @JsonProperty("jobCodeList")
    private List<Long> jobCodeList;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "updatedBy")
    private String updatedBy;
}
