package com.gmf.user_management.masterData.licenseType;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeDTO;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeRequestDto;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeResponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/license_type")
public class LicenseTypeController {
    @Autowired
    private LicenseTypeService licenseTypeService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<LicenseTypeResponDTO>> createLicenseType(
            @RequestBody @Valid LicenseTypeDTO request
    ) throws Exception {
        LicenseTypeResponDTO response = licenseTypeService.createLicenseType(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_license_type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<LicenseTypeResponDTO>> updateLicenseType(
            @PathVariable Long id_license_type,
            @RequestBody @Valid LicenseTypeDTO request
    ) throws Exception {
        LicenseTypeResponDTO response = licenseTypeService.updateLicenseType(id_license_type, request);

        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value ="/by-id/{id_license_type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteLicenseType(
            @PathVariable Long id_license_type
    ){
        return new HttpResponseDTO<>(licenseTypeService.deleteLicenseType(id_license_type))
                .setResponseHeaders("id_license_type", id_license_type)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>> getAllLicenseType(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            LicenseTypeRequestDto requestDto
    ){
        Object allLicenseType = licenseTypeService.getAllLicenseType(page, size, requestDto);
        return new HttpResponseDTO<>(allLicenseType, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("userPaginationRequest", requestDto)
                .toResponse();

    }


    @GetMapping("/by-id/{id_license_type}")
    public ResponseEntity<HttpResponseDTO<LicenseTypeResponDTO>> getLicenseTypeById(
            @PathVariable @IsNumeric @IsRequired Long id_license_type
    )throws NotFoundException {
        return new HttpResponseDTO<>(licenseTypeService.getLicenseTypeById(id_license_type), HttpStatus.OK)
                .setResponseHeaders("id_license_type", id_license_type)
                .toResponse();
    }

}
