package com.gmf.user_management.masterData.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.IdentityType;
import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.partner.entities.PartnerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO {

    @JsonProperty("partnerList")
    private List<Long> partnerList;

    @JsonProperty("licenseTypeList")
    private List<Long> licenseTypeList;

    @JsonProperty("personalName")
    private String personalName;

    @JsonProperty("personalNumber")
    private String personalNumber;

    @JsonProperty("personalPicture")
    private List<MultipartFile> personalPicture;

    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @JsonProperty("contactNumber")
    private String contactNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("identityNumber")
    private String identityNumber;

    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @JsonProperty("dinas")
    private String dinas;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("isPic")
    private Boolean isPic;

    @JsonProperty("passCardNumber")
    private String passCardNumber;

    @JsonProperty("activeStatus")
    @Enumerated(EnumType.STRING)
    private Status activeStatus;

    @JsonProperty("expiredDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiredDate;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
    private String searchTerm;
    private Integer page;
    private Integer size;
}
