package com.gmf.user_management.masterData.applicationLicense.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import org.springframework.http.ResponseEntity;

public interface ApplicationLicenseController {
    ResponseEntity<HttpResponseDTO<ApplicationLicenseResponDTO>>createLicense(ApplicationLicenseDTO request);
    ResponseEntity<HttpResponseDTO<ApplicationLicenseResponDTO>>updateLicense(Long idApplicationLicense, ApplicationLicenseDTO request)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Boolean>>deleteLicense(Long idApplicationLicense);
    ResponseEntity<HttpResponseDTO<PaginationUtil<ApplicationLicenseEntity, ApplicationLicenseDTO>>> getAllLicense(Integer page, Integer size, ApplicationLicenseDTO requestDTO);
}
