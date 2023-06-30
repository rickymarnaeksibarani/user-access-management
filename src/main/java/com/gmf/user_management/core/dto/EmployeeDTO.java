package com.gmf.user_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String personalNumber;
    private String personalName;
    private String personalTitle;
    private String personalUnit;
    private String personalEmail;
    private String personalImage;
    private Boolean isGmfEmployee;
}
