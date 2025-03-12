package com.gmf.user_management.modules.usersAccessDomain.dto;

import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDomainResponDTO {
    private Long idUserAccessDomain;
    private List<PersonalDTOtoUAD> personalList;
    private Boolean isNetworkAccess = false;
    private Boolean isDomainAccess = false;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
