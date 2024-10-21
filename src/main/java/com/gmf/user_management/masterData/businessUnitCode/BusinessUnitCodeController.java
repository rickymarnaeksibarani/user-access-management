package com.gmf.user_management.masterData.businessUnitCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/business_unit_code")
public class BusinessUnitCodeController {
    @Autowired
    private BusinessUnitCodeService businessUnitCodeService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> createBusinessUnitCode(
            @RequestBody @Valid BusinessUnitCodeDTO request
    ) throws Exception {
        BusinessUnitCodeResponDTO response = businessUnitCodeService.createBusinessUnitCode(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/{id_business_unit_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> updateBusinessUnitCode(
            @PathVariable Long id_business_unit_code,
            @RequestBody @Valid BusinessUnitCodeDTO request
    ) throws Exception {
        BusinessUnitCodeResponDTO response = businessUnitCodeService.updateBusinessUnitCode(id_business_unit_code, request);

        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value ="/{id_business_unit_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteBusinessUnitCode(
            @PathVariable Long id_business_unit_code
    ){
        return new HttpResponseDTO<>(businessUnitCodeService.deleteBusinessUnitCode(id_business_unit_code))
                .setResponseHeaders("id_business_unit_code", id_business_unit_code)
                .toResponse();
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpResponseDTO<Object>> getAllBusinessUnitCode(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "20") Integer size,
//            BusinessUnitCodeRequestDto requestDto
//    ){
//        Object allBusinessUnitCode = businessUnitCodeService.getAllBusinessUnitCode(page, size, requestDto);
//        return new HttpResponseDTO<>(allBusinessUnitCode, HttpStatus.OK)
//                .setResponseHeaders("page", page)
//                .setResponseHeaders("size", size)
//                .setResponseHeaders("userPaginationRequest", requestDto)
//                .toResponse();
//
//    }

//
//    @GetMapping("/by-id/{id_job_code}")
//    public ResponseEntity<HttpResponseDTO<JobCodeResponeDTO>>getJobCodeById(
//            @PathVariable @IsNumeric @IsRequired Long id_job_code
//    )throws NotFoundException {
//        return new HttpResponseDTO<>(jobCodeService.getJobCodeById(id_job_code), HttpStatus.OK)
//                .setResponseHeaders("id_job_code", id_job_code)
//                .toResponse();
//    }
}
