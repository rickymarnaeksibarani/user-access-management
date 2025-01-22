package com.gmf.user_management.modules.businessUnitCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeRequestDto;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/business_unit_code")
@RequiredArgsConstructor
public class BusinessUnitCodeController {

    private final BusinessUnitCodeService businessUnitCodeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> createBusinessUnitCode(
            @RequestBody @Valid BusinessUnitCodeDTO request
    ){
        BusinessUnitCodeResponDTO response = businessUnitCodeService.createBusinessUnitCode(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_business_unit_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> updateBusinessUnitCode(
            @PathVariable Long id_business_unit_code,
            @RequestBody @Valid BusinessUnitCodeDTO request
    ){
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
    public ResponseEntity<HttpResponseDTO<Object>> getAllBusinessUnitCode(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            BusinessUnitCodeRequestDto requestDto) {
        Object response = businessUnitCodeService.getAllBusinessUnitCode(page, size, requestDto);

        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("request", "getAllBusinessUnitCode")
                .toResponse();
    }

    @GetMapping("/by-id/{id_business_unit_code}")
    public ResponseEntity<HttpResponseDTO<BusinessUnitCodeResponDTO>> getBusinessUnitCodeById(
            @PathVariable @IsNumeric @IsRequired Long id_business_unit_code
    ){
        return new HttpResponseDTO<>(businessUnitCodeService.getBusinessUnitCodeById(id_business_unit_code), HttpStatus.OK)
                .setResponseHeaders("id_business_unit_code", id_business_unit_code)
                .toResponse();
    }

    @GetMapping(value = "/by-dinas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<BusinessUnitCodeEntity, BusinessUnitCodeEntity>>> getBusinessUnitCodeByDinas(
            @RequestParam String dinas,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PaginationUtil<BusinessUnitCodeEntity, BusinessUnitCodeEntity> response = businessUnitCodeService.getBusinessUnitCodeByDinas(dinas, page, size);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("dinas", dinas)
                .toResponse();
    }

}
