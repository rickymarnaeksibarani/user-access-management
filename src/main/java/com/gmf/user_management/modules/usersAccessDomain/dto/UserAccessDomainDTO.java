package com.gmf.user_management.modules.usersAccessDomain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDomainDTO {
    @JsonProperty("personalList")
    private List<Long> personalList;

    @JsonProperty("isNetworkAccess")
    private Boolean isNetworkAccess = false;

    @JsonProperty("isDomainAccess")
    private Boolean isDomainAccess = false;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
