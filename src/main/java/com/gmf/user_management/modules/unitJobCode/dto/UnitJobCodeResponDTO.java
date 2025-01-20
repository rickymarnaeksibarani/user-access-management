package com.gmf.user_management.modules.unitJobCode.dto;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
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
public class UnitJobCodeResponDTO {
    private Long idUnitJobCode;
    private List<UnitEntity> unitList;
    private List<JobCodeEntity> jobCodeList;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private long jobCodeCount;
    private long unitCount;
}
