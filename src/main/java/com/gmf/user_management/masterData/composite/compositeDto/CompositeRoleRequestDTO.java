package com.gmf.user_management.masterData.composite.compositeDto;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompositeRoleRequestDTO {
    @Sanitizer
    private String searchTerm;
}
