package com.gmf.user_management.masterData.businessUnitCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeRequestDto;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping(value = "/by-id/{id_business_unit_code}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @DeleteMapping(value ="/by-id/{id_business_unit_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteBusinessUnitCode(
            @PathVariable Long id_business_unit_code
    ){
        return new HttpResponseDTO<>(businessUnitCodeService.deleteBusinessUnitCode(id_business_unit_code))
                .setResponseHeaders("id_business_unit_code", id_business_unit_code)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllBusinessUnitCode(
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "20")Integer size,
            BusinessUnitCodeRequestDto businessUnitCodeRequestDto
    ){
        Object allBusiness = businessUnitCodeService.getAllBusinessUnitCode(page, size, businessUnitCodeRequestDto);
        return new HttpResponseDTO<>(allBusiness, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("pagination", businessUnitCodeRequestDto)
                .toResponse();
    }

    @GetMapping("/by-id/{id_business_unit_code}")
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> getBusinessUnitCodeById(
            @PathVariable @IsNumeric @IsRequired Long id_business_unit_code
    )throws NotFoundException {
        return new HttpResponseDTO<>(businessUnitCodeService.getBusinessUnitCodeById(id_business_unit_code), HttpStatus.OK)
                .setResponseHeaders("id_business_unit_code", id_business_unit_code)
                .toResponse();
    }

    @GetMapping("/by-dinas")
    public ResponseEntity<HttpResponseDTO<List<BusinessUnitCodeResponDTO>>> getBusinessUnitCodeByDinas(
            @RequestParam String dinas
    ) {
        List<BusinessUnitCodeResponDTO> response = businessUnitCodeService.getBusinessUnitCodeByDinas(dinas);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("dinas", dinas)
                .toResponse();
    }

}
