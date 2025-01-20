package com.gmf.user_management.modules.unit.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitRequestDto {
    @Sanitizer
    private String unit;
}
