package com.gmf.user_management.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActiveDTO {

    @JsonProperty("id")
    private Long idActiveUser;

    @JsonProperty("sourceId")
    private Long sourceId;

    @JsonProperty("companySource")
    private String companyName;

    @JsonProperty("personalNumber")
    private String aliasPersonalNumber;

    @JsonProperty("passCardNumber")
    private String passCardNumber;

    @JsonProperty("username")
    private String username;

    @JsonProperty("workStation")
    private String workstation;

    @JsonProperty("userStatus")
    private String status;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userEmail")
    private String email;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
