package com.gmf.user_management.masterData.userLicense.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseResponeDTO;
import org.springframework.http.ResponseEntity;

public interface UserLicenseController {
    ResponseEntity<HttpResponseDTO<UserLicenseResponeDTO>> createUserLicense(UserLicenseDTO requestDto);
    ResponseEntity<HttpResponseDTO<UserLicenseResponeDTO>> updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Boolean>> deleteUserLicense(Long idUserLicense)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<UserLicenseDTO[]>> getPersonalIdByApplicationLicenseId(Long applicationLicenseId)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<UserLicenseDTO[]>>getApplicationLicenseIdByUserId(Long userId)throws NotFoundException;
}
