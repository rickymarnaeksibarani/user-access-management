package com.gmf.user_management.modules.applicationLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseRequest;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;

public interface ApplicationLicenseService {
    ApplicationLicenseResponDTO createLicense(ApplicationLicenseDTO request);
    ApplicationLicenseResponDTO updateLicense(Long idApplicationLicense, ApplicationLicenseDTO request)throws NotFoundException;
    Boolean deleteLicense(Long idApplicationLicense);
    PaginationUtil<ApplicationLicenseEntity, ApplicationLicenseResponDTO> getAllLicense(Integer page, Integer size, ApplicationLicenseRequest requestDTO);

    ApplicationLicenseResponDTO getApplicationLicenseById(Long applicationLicenseId) throws NotFoundException;
}
