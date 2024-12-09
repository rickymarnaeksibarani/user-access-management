package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.core.enums.IdentityType;
import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalResponDTO {
    private Long idPersonal;
    private Map<String, Object> partnerExternal;
    private List<LicenseTypeEntity> licenseTypeList;
    private String personalName;
    private String personalNumber;
    private List<ApplicationFileDTO> personalPicture;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;
    private String identityNumber;
    private IdentityType identityType;
    private String dinas;
    private String unit;
    private String uid;
    private Boolean isPic;
    private String passCardNumber;
    private Status activeStatus;
    private Date expiredDate;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
