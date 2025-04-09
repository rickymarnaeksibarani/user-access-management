package com.gmf.user_management.modules.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.IdentityType;
import com.gmf.user_management.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO {

    @JsonProperty("partnerExternal")
    @NotNull
    private Long partnerExternal;

    @JsonProperty("licenseTypeList")
    private List<Long> licenseTypeList;

    @JsonProperty("sapLoginTypeList")
    private List<Long> sapLoginTypeList;

    @JsonProperty("personalName")
    @NotEmpty
    private String personalName;

    @JsonProperty("personalNumber")
    private String personalNumber;

    @JsonProperty("personalPicture")
    private List<MultipartFile> personalPicture;

    @JsonProperty("dateOfBirth")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @JsonProperty("contactNumber")
    private String contactNumber;

    @JsonProperty("email")
    @NotEmpty
    private String email;

    @JsonProperty("identityNumber")
    @NotEmpty
    private String identityNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IdentityType identityType;

    @JsonProperty("dinas")
    @NotEmpty
    private String dinas;

    @JsonProperty("unit")
    @NotEmpty
    private String unit;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("isPic")
    @NotNull
    private Boolean isPic;

    @JsonProperty("passCardNumber")
    private String passCardNumber;

    @JsonProperty("activeStatus")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status activeStatus;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    private Date startDate;

    @JsonProperty("expiredDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    private Date expiredDate;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    private String searchTerm;
    private Integer page;
    private Integer size;
}
