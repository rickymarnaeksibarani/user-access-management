package com.gmf.user_management.masterData.usersAccessDomain.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.domains.user.UserService;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.masterData.usersAccessDomain.dto.UserAccessDomainResponDTO;
import com.gmf.user_management.masterData.usersAccessDomain.service.UserAccessDomainService;
import com.gmf.user_management.masterData.usersAccessDomain.service.UserAccessDomainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/UserAccessDomain")
@Validated
public class UserAccessDomainControllerImpl {
    @Autowired
    private UserAccessDomainService userAccessDomainService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>>createUserAccessDomain(
            @RequestPart @Valid UserAccessDomainDTO request
    ){
        UserAccessDomainResponDTO respone = userAccessDomainService.createUserAccessDomain(request);
        return new HttpResponseDTO<>(respone, HttpStatus.CREATED)
                .setResponseHeaders("respon", respone)
                .toResponse();
    }
}
