package com.gmf.user_management.masterData.personal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
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

//    @DeleteMapping(value = "/by-id/{id_personal}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpResponseDTO<Boolean>> deletePersonal(
//            @PathVariable Long id_personal
//    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        return new HttpResponseDTO<>(personalService.deletePersonal(id_personal))
//                .setResponseHeaders("id_personal", id_personal)
//                .toResponse();
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllPersonal(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
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

    @GetMapping("/by-number/{personalNumber}")
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>> getPersonalByPersonalNumber(
            @PathVariable String personalNumber
    ) throws NotFoundException, JsonProcessingException {
        PersonalResponDTO response = personalService.getPersonalByPersonalNumber(personalNumber);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("personalNumber", personalNumber)
                .toResponse();
    }

    @GetMapping("/by-dinas/{dinas}")
    public ResponseEntity<HttpResponseDTO<List<PersonalResponDTO>>> getPersonalByDinas(
            @PathVariable String dinas
    ) throws NotFoundException, JsonProcessingException {
        List<PersonalResponDTO> response = personalService.getPersonalByDinas(dinas);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("dinas", dinas)
                .toResponse();
    }

    @GetMapping(value = "/count-uid-by-dinas", produces = MediaType.APPLICATION_JSON_VALUE)
    public String countUIDByDinas() {
        return personalService.countUIDByDinas();
    }


    @GetMapping("/by-partner-id/{partnerId}")
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>> getPersonalByPartnerId(
            @PathVariable Long partnerId
    ) {
        try {
            PersonalResponDTO response = personalService.getPersonalByPartnerId(partnerId);
            return new HttpResponseDTO<>(response, HttpStatus.OK)
                    .setResponseHeaders("partnerId", partnerId)
                    .toResponse();
        } catch (ResponseStatusException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
