package com.gmf.user_management.masterData.jobCode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCodeResponeDTO {
    private Long id_job_code;
    private String job_code;
    private String job_position;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String update_by;
}
