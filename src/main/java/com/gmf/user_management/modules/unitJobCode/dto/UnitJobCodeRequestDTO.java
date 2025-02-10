package com.gmf.user_management.modules.unitJobCode.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitJobCodeRequestDTO {
    @Sanitizer
    private String searchTerm;
    private String filterByDinas;
    private String filterByUnit;
    private String filterByJobCode;

}
