package com.gmf.user_management.masterData.jobCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeResponeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/job-code")
public class JobCodeController {
    @Autowired
    private JobCodeService jobCodeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>>createJobCode(
            @RequestBody @Valid JobCodeDTO request
    ) throws Exception {
        JobCodeResponeDTO response = jobCodeService.createJobCode(request);

        JobCodeDTO updatedRequest = JobCodeDTO.builder()
                .id_job_code(response.getId_job_code())
                .job_code(response.getJob_code())
                .job_position(response.getJob_position())
                .created_by(response.getCreated_by())
                .created_at(response.getCreated_at())
                .updated_by(response.getUpdate_by())
                .updated_at(response.getUpdated_at())
                .build();
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", updatedRequest)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>> updateJobCode(
            @PathVariable Long id_job_code,
            @RequestBody @Valid JobCodeDTO request
    ) throws Exception {
        JobCodeResponeDTO response = jobCodeService.updateJobCode(id_job_code, request);

        // Build a fully populated DTO for headers using the updated data
        JobCodeDTO updatedRequest = JobCodeDTO.builder()
                .id_job_code(response.getId_job_code())      // Use updated ID
                .job_code(response.getJob_code())            // Use updated job code
                .job_position(response.getJob_position())    // Use updated job position
                .created_at(response.getCreated_at())        // Include created_at (existing or new)
                .created_by(response.getCreated_by())        // Include created_by
                .updated_at(response.getUpdated_at())        // Use updated_at
                .updated_by(response.getUpdate_by())         // Use updated_by
                .build();
        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", updatedRequest)
                .toResponse();
    }
}
