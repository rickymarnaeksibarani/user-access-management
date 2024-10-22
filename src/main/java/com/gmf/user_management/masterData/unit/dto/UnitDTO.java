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
//    @JsonProperty("id_unit")
//    private Long id_unit;

    @JsonProperty("business_unit_code_id")
    private List<Long> business_unit_code_id;

    @JsonProperty("unit")
    private String unit;

//    @JsonProperty("created_at")
//    private LocalDateTime created_at;

    @JsonProperty("created_by")
    private String created_by;

//    @JsonProperty("updated_at")
//    private LocalDateTime updated_at;

    @JsonProperty("updated_by")
    private String updated_by;
}
