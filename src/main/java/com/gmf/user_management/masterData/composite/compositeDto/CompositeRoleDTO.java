package com.gmf.user_management.masterData.composite.compositeDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeRoleDTO {
    @JsonProperty("jobCodeList")
    private List<Long> jobCodeList;

    @JsonProperty("compositeRole")
    private String compositeRole;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
