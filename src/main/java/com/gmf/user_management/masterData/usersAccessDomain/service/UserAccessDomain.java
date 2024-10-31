package com.gmf.user_management.masterData.usersAccessDomain.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainResponDTO;

public interface UserAccessDomain {
    UserAccessDomainResponDTO createUserAccessDomain(UserAccessDomainDTO request);
    UserAccessDomainResponDTO updateUserAccessDomain(Long idUserAccessDomain,UserAccessDomainDTO request) throws NotFoundException;
    Boolean deleteUserAccessDomain(Long idUser);
    UserAccessDomainResponDTO getUserAccessDomainByPersonalId(Long personalId, UserAccessDomainDTO request)throws NotFoundException;
}
