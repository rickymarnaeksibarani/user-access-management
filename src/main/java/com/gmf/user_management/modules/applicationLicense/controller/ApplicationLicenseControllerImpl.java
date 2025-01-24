package com.gmf.user_management.modules.applicationLicense.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseRequest;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.modules.applicationLicense.service.ApplicationLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/applicationLicense")
@RequiredArgsConstructor
public class ApplicationLicenseControllerImpl {

    private final ApplicationLicenseService applicationLicenseService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<ApplicationLicenseResponDTO>> createLicense(
            @RequestBody @Valid ApplicationLicenseDTO request
    ){
        ApplicationLicenseResponDTO respone = applicationLicenseService.createLicense(request);
        return new HttpResponseDTO<>(respone, HttpStatus.CREATED)
                .setResponseHeaders("respon", respone)
                .toResponse();
    }

    @PutMapping (value = "/by-id/{idApplicationLicense}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<ApplicationLicenseResponDTO>>updateLicense(
            @RequestBody @Valid ApplicationLicenseDTO request,
            @PathVariable Long idApplicationLicense
    ) throws NotFoundException {
        ApplicationLicenseResponDTO responDTO = applicationLicenseService.updateLicense(idApplicationLicense, request);
        return new HttpResponseDTO<>(responDTO, HttpStatus.OK)
                .setResponseHeaders("responDTO", responDTO)
                .toResponse();
    }

    @DeleteMapping(value = "/by-id/{idApplicationLicense}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteLicense(
            @PathVariable Long idApplicationLicense
    ) {
       return new HttpResponseDTO<>(applicationLicenseService.deleteLicense(idApplicationLicense))
               .setResponseHeaders("idApplicationLicense", idApplicationLicense)
               .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllLicense(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            ApplicationLicenseRequest requestDto
    ){
        Object allComposite = applicationLicenseService.getAllLicense(page, size, requestDto);
        return new HttpResponseDTO<>(allComposite, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }

    @GetMapping("/by-id/{applicationLicenseId}")
    public ResponseEntity<HttpResponseDTO<ApplicationLicenseResponDTO>> getApplicationLicenseById(
            @PathVariable @IsNumeric @IsRequired Long applicationLicenseId
    )throws NotFoundException {
        return new HttpResponseDTO<>(applicationLicenseService.getApplicationLicenseById(applicationLicenseId), HttpStatus.OK)
                .setResponseHeaders("application_license_id", applicationLicenseId)
                .toResponse();
    }
}
