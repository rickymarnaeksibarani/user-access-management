package com.gmf.user_management.modules.usersAccessDomain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDTOtoUAD {
    private Long personalId;
    private String personalName;
    private String email;
    private String identityNumber;
    private Boolean isPic;
}
