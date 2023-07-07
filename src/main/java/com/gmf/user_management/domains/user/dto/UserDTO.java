package com.gmf.user_management.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.validations.IsNumeric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    private Long idUserDetail;

    @JsonProperty("firstName")
    @NotNull
    private String firstName;

    @JsonProperty("lastName")
    @NotNull
    private String lastName;

    @JsonProperty("userEmail")
    @Email
    private String email;

    @JsonProperty("isPic")
    private Boolean isPic;

    @JsonProperty("identityNumber")
    @IsNumeric
    private String identityNumber;

    @JsonProperty("identityType")
    private String identityType;

    @JsonProperty("dateOfBirth")
    @NotNull
    private String dateOfBirth;

    @JsonProperty("status")
    @NotNull
    private String activeStatus;

    @JsonProperty("sourceId")
    @NotNull
    private Long userSourceId;

    @JsonProperty("retiredAt")
    private LocalDateTime retiredAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
