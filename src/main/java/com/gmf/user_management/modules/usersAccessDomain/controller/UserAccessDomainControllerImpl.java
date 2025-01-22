package com.gmf.user_management.modules.usersAccessDomain.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainResponDTO;
import com.gmf.user_management.modules.usersAccessDomain.service.UserAccessDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/userAccessDomain")
@Validated
public class UserAccessDomainControllerImpl {

    private final UserAccessDomainService userAccessDomainService;
    public UserAccessDomainControllerImpl(UserAccessDomainService userAccessDomainService){
        this.userAccessDomainService = userAccessDomainService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>>createUserAccessDomain(
            @RequestBody @Valid UserAccessDomainDTO request
    ){
        UserAccessDomainResponDTO respone = userAccessDomainService.createUserAccessDomain(request);
        return new HttpResponseDTO<>(respone, HttpStatus.CREATED)
                .setResponseHeaders("respon", respone)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{idUserAccessDomain}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>>updateUserAccessDomain(
        @RequestBody @Valid UserAccessDomainDTO request,
        @PathVariable Long idUserAccessDomain
    )throws Exception{
        UserAccessDomainResponDTO responDTO = userAccessDomainService.updateUserAccessDomain(idUserAccessDomain, request);
        return new HttpResponseDTO<>(responDTO, HttpStatus.OK)
                .setResponseHeaders("responDTO", responDTO)
                .toResponse();

    }

    @DeleteMapping(value = "/by-id/{idUserAccessDomain}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>>deleteUserAccessDomain(
            @PathVariable Long idUserAccessDomain
    ){
        return new HttpResponseDTO<>(userAccessDomainService.deleteUserAccessDomain(idUserAccessDomain))
                .setResponseHeaders("idUserAccessDomain", idUserAccessDomain)
                .toResponse();
    }

    @GetMapping(value = "/by-id/{personal_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserAccessDomainResponDTO>>getUserAccessDomainByPersonalId(
            @PathVariable Long personal_id
    ) throws NotFoundException {
        UserAccessDomainResponDTO response = userAccessDomainService.getUserAccessDomainByPersonalId(personal_id, null);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("response", response)
                .toResponse();
    }

}
