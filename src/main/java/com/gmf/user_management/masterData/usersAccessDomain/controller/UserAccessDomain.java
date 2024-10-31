package com.gmf.user_management.masterData.usersAccessDomain.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainResponDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface UserAccessDomain {
    ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>> createUserAccessDomain(UserAccessDomainDTO request);
    ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>> updateUserAccessDomain(Long idUserAccessDomain,UserAccessDomainDTO request) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Boolean>>deleteUserAccessDomain(Long idUser)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>>getUserAccessDomainByPersonalId(Long personalId, UserAccessDomainDTO request)throws NotFoundException;
}
