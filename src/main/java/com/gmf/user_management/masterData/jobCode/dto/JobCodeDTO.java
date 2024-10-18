package com.gmf.user_management.masterData.jobCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCodeDTO {
    @JsonProperty("id_job_code")
    private Long id_job_code;
    @JsonProperty("job_code")
    private String job_code;
    @JsonProperty("job_position")
    private String job_position;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("created_by")
    private String created_by;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

    @JsonProperty("updated_by")
    private String updated_by;
}
