package com.gmf.user_management.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @JsonProperty("id")
    private Long idActiveUser;

    @JsonProperty("personalNumber")
    @NotNull
    private String personalNumber;

    @NotNull
    @JsonProperty("username")
    private String username;

    @NotNull
    private String password;

    @JsonProperty("passCardNumber")
    @NotNull
    private String passCardNumber;

    @JsonProperty("status")
    @NotNull
    private String activeStatus;

    @JsonProperty("title")
    private String title;

    @JsonProperty("workstation")
    @NotNull
    private String workStation;

    @JsonProperty("userDetailId")
    private Long userDetailId;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
