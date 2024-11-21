package com.gmf.user_management.masterData.applicationLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseRequest;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;

public interface ApplicationLicenseService {
    ApplicationLicenseResponDTO createLicense(ApplicationLicenseDTO request);
    ApplicationLicenseResponDTO updateLicense(Long idApplicationLicense, ApplicationLicenseDTO request)throws NotFoundException;
    Boolean deleteLicense(Long idApplicationLicense);
    PaginationUtil<ApplicationLicenseEntity, ApplicationLicenseResponDTO> getAllLicense(Integer page, Integer size, ApplicationLicenseRequest requestDTO);

}
