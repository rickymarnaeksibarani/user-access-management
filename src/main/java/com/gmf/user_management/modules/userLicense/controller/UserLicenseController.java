package com.gmf.user_management.modules.userLicense.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseResponeDTO;
import com.gmf.user_management.modules.userLicense.entities.UserLicenseEntity;
import com.gmf.user_management.modules.userLicense.service.UserLicenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/userLicense")
@RequiredArgsConstructor
public class UserLicenseController {

    private final UserLicenseServiceImpl userLicenseService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserLicenseResponeDTO>>createUserLicense(
            @RequestBody @Valid UserLicenseDTO request
            ){
        UserLicenseResponeDTO respone = userLicenseService.createUserLicense(request);
        return new HttpResponseDTO<>(respone, HttpStatus.CREATED)
                .setResponseHeaders("respon", respone)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{idUserLicense}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserLicenseResponeDTO>>updateUserLicense(
            @RequestBody @Valid UserLicenseDTO request,
            @PathVariable Long idUserLicense
    ) throws NotFoundException {
        UserLicenseResponeDTO responeDTO = userLicenseService.updateUserLicense(idUserLicense, request);
        return new HttpResponseDTO<>(responeDTO, HttpStatus.OK)
                .setResponseHeaders("responDTO", responeDTO)
                .toResponse();
    }

    @DeleteMapping(value = "/by-id/{idUserLicense}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>>deleteUserLicense(
            @PathVariable Long idUserLicense
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userLicenseService.deleteUserLicense(idUserLicense))
                .setResponseHeaders("idUserLicense", idUserLicense)
                .toResponse();
    }

    @GetMapping(value = "/personal/{applicationLicenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UserLicenseEntity, UserLicenseEntity>>> getPersonalIdByApplicationLicenseId(
            @PathVariable Long applicationLicenseId,
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ) {
        PaginationUtil<UserLicenseEntity, UserLicenseEntity> response = userLicenseService.getPersonalIdByApplicationLicenseId(applicationLicenseId, page, size);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("applicationLicenseId", applicationLicenseId)
                .toResponse();
    }

    @GetMapping(value = "/application/{idUserLicense}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UserLicenseEntity, UserLicenseEntity>>> getApplicationLicenseIdByUserId(
            @PathVariable Long idUserLicense,
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ) {
        PaginationUtil<UserLicenseEntity, UserLicenseEntity> response = userLicenseService.getApplicationLicenseIdByUserId(idUserLicense, page, size);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("idUserLicense", idUserLicense)
                .toResponse();
    }

    @GetMapping("/count-personal-applications")
    public ResponseEntity<Map<String, Long>> countPersonalApplicationsByDinas(
            @RequestParam(required = false) String dinas
    ) {
        Map<String, Long> result = userLicenseService.countPersonalApplicationsByDinas(dinas);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count-total-application-licenses")
    public ResponseEntity<String> countTotalApplicationLicenses() {
        String result = userLicenseService.countTotalApplicationLicenses();
        return ResponseEntity.ok(result);
    }
}
