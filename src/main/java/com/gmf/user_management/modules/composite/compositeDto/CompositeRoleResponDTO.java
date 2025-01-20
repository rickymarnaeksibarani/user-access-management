package com.gmf.user_management.modules.composite.compositeDto;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
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
public class CompositeRoleResponDTO {
    private Long idCompositeRole;
    private List<JobCodeEntity> jobCodeList;
    private String compositeRole;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private long jobCodeCount;
}
