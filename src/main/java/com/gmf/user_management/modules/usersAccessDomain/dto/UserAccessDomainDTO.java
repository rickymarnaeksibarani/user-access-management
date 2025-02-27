package com.gmf.user_management.modules.usersAccessDomain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDomainDTO {
    @JsonProperty("personalList")
    @NonNull
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
