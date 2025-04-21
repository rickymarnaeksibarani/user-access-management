package com.gmf.user_management.modules.usersAccessDomain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDomainDTO {
    @JsonProperty("personalId")
    @NonNull
    private Long personalId;

    @JsonProperty("isNetworkAccess")
    private Boolean isNetworkAccess = false;

    @JsonProperty("isDomainAccess")
    private Boolean isDomainAccess = false;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
