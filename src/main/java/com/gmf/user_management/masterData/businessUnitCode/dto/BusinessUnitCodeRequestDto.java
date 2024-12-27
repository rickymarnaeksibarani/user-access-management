package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BusinessUnitCodeRequestDto {
    @Sanitizer
    private String searchTerm;
    private List<String> dinas;
    private List<String> partnerName;
}
