package com.gmf.user_management.masterData.jobCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeRequestDto;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeResponeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/jobCode")
public class JobCodeController {
    @Autowired
    private JobCodeService jobCodeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>>createJobCode(
            @RequestBody @Valid JobCodeDTO request
    ){
        JobCodeResponeDTO response = jobCodeService.createJobCode(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>> updateJobCode(
            @PathVariable Long id_job_code,
            @RequestBody @Valid JobCodeDTO request
    ){
        JobCodeResponeDTO response = jobCodeService.updateJobCode(id_job_code, request);
        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", response)
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
            @RequestParam(defaultValue = "10") Integer size,
            JobCodeRequestDto jobCodeRequestDto
    ){
        Object allJobCode = jobCodeService.getAllJobCode(page, size, jobCodeRequestDto);
        return new HttpResponseDTO<>(allJobCode, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("userPaginationRequest", jobCodeRequestDto)
                .toResponse();

    }

    @GetMapping(value = "/by-id/{id_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>>getJobCodeById(
            @PathVariable @IsNumeric @IsRequired Long id_job_code
    )throws NotFoundException {
        return new HttpResponseDTO<>(jobCodeService.getJobCodeById(id_job_code), HttpStatus.OK)
                .setResponseHeaders("id_job_code", id_job_code)
                .toResponse();
    }
}
