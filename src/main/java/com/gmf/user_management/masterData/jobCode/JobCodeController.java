package com.gmf.user_management.masterData.jobCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.domains.user.UserPaginationRequest;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeRequestDto;
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
                .id_job_code(response.getId_job_code())
                .job_code(response.getJob_code())
                .job_position(response.getJob_position())
                .created_at(response.getCreated_at())
                .created_by(response.getCreated_by())
                .updated_at(response.getUpdated_at())
                .updated_by(response.getUpdate_by())
                .build();
        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", updatedRequest)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value ="/{id_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteJobCode(
            @PathVariable Long id_job_code
    ){
        return new HttpResponseDTO<>(jobCodeService.deleteJobCode(id_job_code))
                .setResponseHeaders("id_job_code", id_job_code)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>> getAllJobCode(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            JobCodeRequestDto jobCodeRequestDto
    ){
        Object allJobCode = jobCodeService.getAllJobCode(page, size, jobCodeRequestDto);
        return new HttpResponseDTO<>(allJobCode, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("userPaginationRequest", jobCodeRequestDto)
                .toResponse();

    }

    @GetMapping("/by-id/{id_job_code}")
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>>getJobCodeById(
            @PathVariable @IsNumeric @IsRequired Long id_job_code
    )throws NotFoundException {
        return new HttpResponseDTO<>(jobCodeService.getJobCodeById(id_job_code), HttpStatus.OK)
                .setResponseHeaders("id_job_code", id_job_code)
                .toResponse();
    }
}
