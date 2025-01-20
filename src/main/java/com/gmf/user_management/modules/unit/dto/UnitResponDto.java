package com.gmf.user_management.modules.unit.dto;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
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
    private Long idUnit;
    private List<BusinessUnitCodeEntity> businessUnitCodeList;
    private String unit;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
