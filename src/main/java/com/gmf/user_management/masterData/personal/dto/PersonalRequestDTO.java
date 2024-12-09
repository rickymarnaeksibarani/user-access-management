package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonalRequestDTO {
    private String filterByName;
    private List<Status> filterByStatus;
    private String searchByName;
}
