package com.gmf.user_management.modules.composite.compositeDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeRoleDTO {
    @JsonProperty("jobCodeList")
    @NotEmpty
    private List<Long> jobCodeList;

    @JsonProperty("compositeRole")
    @NotEmpty
    private String compositeRole;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
