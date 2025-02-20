package com.gmf.user_management.modules.businessUnitCode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUnitCodeResponDTO {
    private Long idBusinessUnitCode;
    private String businessUnitCode;
    private Map<String, Object> partnerExternal;
    private String partnerName;
    private String description;
    private String dinas;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
