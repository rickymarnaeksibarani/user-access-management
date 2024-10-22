package com.gmf.user_management.masterData.unit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponDto {
    private Long id_unit;
    private List<BusinessUnitCodeEntity> business_unit_code_id;
    private String unit;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;
}
