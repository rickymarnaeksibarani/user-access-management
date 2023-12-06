package com.gmf.user_management.domains.user.dto;

import lombok.Data;

@Data
public class PersonalInformationDTO {
    private String personalNumber;
    private String personalName;
    private String personalTitle;
    private String personalEmail;
    private String personalUnit;
    private String personalImage;
    private String personalGroup;
    private String personalSubGroup;
    private String personalJob;
    private String personalSuperior;
    private Boolean isGmfEmployee;
}
