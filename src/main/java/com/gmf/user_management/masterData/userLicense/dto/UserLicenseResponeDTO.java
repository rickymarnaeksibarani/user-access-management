package com.gmf.user_management.masterData.userLicense.dto;

import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
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
public class UserLicenseResponeDTO {
    private Long idUserLicense;
    private List<ApplicationLicenseEntity> applicationLicenseList;
    private List<PersonalEntity> personalList;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
