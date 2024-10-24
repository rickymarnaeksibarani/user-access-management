package com.gmf.user_management.masterData.unit.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitRequestDto {
    @Sanitizer
    private String searchTerm;
}
