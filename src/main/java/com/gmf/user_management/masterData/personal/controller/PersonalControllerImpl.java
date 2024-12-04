package com.gmf.user_management.masterData.personal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.personal.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/personal")
@Validated
@Slf4j
public class PersonalControllerImpl {
    @Autowired
    private PersonalService personalService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>> createPersonal(
            @RequestPart @Valid PersonalDTO request,
            @RequestPart(value = "personalPicture", required = false) List<MultipartFile> personalPicture
    ) throws JsonProcessingException {
        if (Objects.nonNull(personalPicture)){
            request.setPersonalPicture(personalPicture);
        }
        PersonalResponDTO response = personalService.createPersonal(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("respon", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_personal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>>updatePersonal(
            @RequestPart @Valid PersonalDTO request,
            @PathVariable Long id_personal,
            @RequestPart(value = "personalPicture", required = false) List<MultipartFile> personalPicture
    )throws Exception{
        if (Objects.nonNull(personalPicture)) {
            request.setPersonalPicture(personalPicture);
        }
        PersonalResponDTO responDto = personalService.updatePersonal(id_personal, request);
        return new HttpResponseDTO<>(responDto,HttpStatus.OK)
                .setResponseHeaders("responDto", responDto)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllPersonal(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            PersonalRequestDTO requestDto
    ) throws JsonProcessingException {
        Object allDataPersonal = personalService.getAllPersonal(page, size, requestDto);
        return new HttpResponseDTO<>(allDataPersonal, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }

    @GetMapping("/by-id/{id_personal}")
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalById(
            @PathVariable Long id_personal
    ) throws NotFoundException, JsonProcessingException {
        return new HttpResponseDTO<>(personalService.getPersonalById(id_personal), HttpStatus.OK)
                .setResponseHeaders("id_personal", id_personal)
                .toResponse();
    }

    @GetMapping(value = "/by-number/{personalNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>> getPersonalByPersonalNumber(
            @PathVariable String personalNumber
    ) throws NotFoundException, JsonProcessingException {
        PersonalResponDTO response = personalService.getPersonalByPersonalNumber(personalNumber);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("personalNumber", personalNumber)
                .toResponse();
    }

    @GetMapping(value = "/by-dinas/{dinas}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity, PersonalEntity>>> getPersonalByDinas(
            @PathVariable String dinas,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) throws NotFoundException, JsonProcessingException {
        PaginationUtil<PersonalEntity, PersonalEntity> response = personalService.getPersonalByDinas(dinas,page, size);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("dinas", dinas)
                .toResponse();
    }

    @GetMapping(value = "/count-uid-by-dinas", produces = MediaType.APPLICATION_JSON_VALUE)
    public String countUIDByDinas() {
        return personalService.countUIDByDinas();
    }

    @GetMapping(value = "/by-partner-id/{partnerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity,PersonalEntity>>> getPersonalByPartnerId(
            @PathVariable Long partnerId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            PaginationUtil<PersonalEntity,PersonalEntity> response = personalService.getPersonalByPartnerId(partnerId, page, size);
            return new HttpResponseDTO<>(response, HttpStatus.OK)
                    .setResponseHeaders("partnerId", partnerId)
                    .setResponseHeaders("page", page)
                    .setResponseHeaders("size", size)
                    .toResponse();
        } catch (ResponseStatusException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/as-pic/by-partner-id/{partnerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity, PersonalEntity>>> getPersonalAsPartnerPIC(
            @PathVariable Long partnerId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            PaginationUtil<PersonalEntity, PersonalEntity> response = personalService.getPersonalAsPartnerPIC(partnerId, page, size);
            return new HttpResponseDTO<>(response, HttpStatus.OK)
                    .setResponseHeaders("partnerId", partnerId)
                    .toResponse();
        } catch (ResponseStatusException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
