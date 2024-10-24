package com.gmf.user_management.masterData.unit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    @JsonProperty("businessUnitCodeList")
    private List<Long> businessUnitCodeList;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
