package com.gmf.user_management.modules.usersAccessDomain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDomainResponDTO {
    private Long idUserAccessDomain;
    private PersonalDTOtoUAD personal;
    private Boolean isNetworkAccess = false;
    private Boolean isDomainAccess = false;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
