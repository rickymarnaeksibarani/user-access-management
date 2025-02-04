package com.gmf.user_management.modules.userLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.modules.userLicense.dto.UserLicensePredicateDto;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseRequestDto;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseResponeDTO;
import com.gmf.user_management.modules.userLicense.entities.UserLicenseEntity;

import java.util.Map;

public interface UserLicenseService {
    UserLicenseResponeDTO createUserLicense(UserLicenseDTO requestDto);
    UserLicenseResponeDTO updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto) throws NotFoundException;
    Boolean deleteUserLicense(Long idUserLicense)throws NotFoundException;
    PaginationUtil<UserLicenseEntity, UserLicenseEntity> getPersonalIdByApplicationLicenseId(Long applicationLicenseId, Integer page, Integer size);
    PaginationUtil<UserLicenseEntity, UserLicenseEntity> getApplicationLicenseIdByUserId(Long idUserLicense,  Integer page, Integer size);
//    Map<String, Long> countPersonalApplicationsByDinas();

    //todo > create new endpoint to get all userLicense. filter > dinas, application name, personal name, company name, unit, personal number, passcard number
    PaginationUtil<UserLicenseEntity, UserLicenseEntity>getAllUserLicense(Integer page, Integer size, UserLicenseRequestDto requestDTO);

    Map<String, Long> countPersonalApplicationsByDinas(String dinas);

    String countTotalApplicationLicenses();
}
