package com.gmf.user_management.masterData.userLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseResponeDTO;

public interface UserLicenseService {
    UserLicenseResponeDTO createUserLicense(UserLicenseDTO requestDto);
    UserLicenseResponeDTO updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto) throws NotFoundException;
    Boolean deleteUserLicense(Long idUserLicense)throws NotFoundException;
    UserLicenseResponeDTO[] getPersonalIdByApplicationLicenseId(Long applicationLicenseId);
    UserLicenseResponeDTO[] getApplicationLicenseIdByUserId(Long userId);
}
