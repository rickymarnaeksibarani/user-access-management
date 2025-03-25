package com.gmf.user_management.modules.usersAccessDomain.dto;

import com.gmf.user_management.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDTOtoUAD {
    private Long personalId;
    private String personalName;
    private String personalNumber;
    private String email;
    private String identityNumber;
    private Boolean isPic;
    private Status activeStatus;
    private Date expiredDate;
}
