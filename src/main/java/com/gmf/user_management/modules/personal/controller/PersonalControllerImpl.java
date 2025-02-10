package com.gmf.user_management.modules.personal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.personal.dto.PersonalDTO;
import com.gmf.user_management.modules.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.modules.personal.dto.PersonalResponDTO;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.personal.service.PersonalService;
import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/personal")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PersonalControllerImpl {

    private final PersonalService personalService;

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
    public ResponseEntity<HttpResponseDTO<Object>> getAllPersonal(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            PersonalRequestDTO requestDto) {
        Object response = personalService.getAllPersonal(page, size, requestDto);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("request", "getAllPersonal")
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

    @GetMapping(value = "/by-dinas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity, PersonalEntity>>> getPersonalByDinas(
            @PathVariable String dinas,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            PersonalRequestDTO requestDTO
    ){
        log.info("dinas {}", dinas);
        PaginationUtil<PersonalEntity, PersonalEntity> response = personalService.getPersonalByDinas(dinas,page, size, requestDTO);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("dinas", dinas)
                .toResponse();
    }

    @GetMapping(value = "/count-uid-by-dinas", produces = MediaType.APPLICATION_JSON_VALUE)
    public String countUIDByDinas() {
        return personalService.countUIDByDinas();
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Map<String, Long>>>countPersonalByLicenseName(){
        Map<String, Long> stats = personalService.countPersonalByLicense();
        return new HttpResponseDTO<>(stats, HttpStatus.OK)
                .setResponseHeaders("statistics", stats)
                .toResponse();
    }

    @GetMapping(value = "/by-partner-id/{partnerExternal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity,PersonalEntity>>> getPersonalByPartnerId(
            @PathVariable Long partnerExternal,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            PersonalRequestDTO requestDTO
    ) {
        try {
            PaginationUtil<PersonalEntity,PersonalEntity> response = personalService.getPersonalByPartnerId(partnerExternal, page, size, requestDTO);
            return new HttpResponseDTO<>(response, HttpStatus.OK)
                    .setResponseHeaders("partnerExternal", partnerExternal)
                    .setResponseHeaders("page", page)
                    .setResponseHeaders("size", size)
                    .toResponse();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching personal data", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred.", e);
        }
    }

    @GetMapping("/sap-login-type/{sapLoginTypeId}")
    public ResponseEntity<PaginationUtil<PersonalEntity, PersonalEntity>> getPersonalBySapLoginTypeId(
            @PathVariable Long sapLoginTypeId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PaginationUtil<PersonalEntity, PersonalEntity> response = personalService.getPersonalBySapLoginTypeId(sapLoginTypeId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/personal-sap-login-type/{personalId}")
    public ResponseEntity<PaginationUtil<SapLoginTypeEntity, SapLoginTypeEntity>> getSapLoginTypeByPersonalId(
            @PathVariable Long personalId,
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ){
       PaginationUtil<SapLoginTypeEntity, SapLoginTypeEntity> sapLoginTypePage = personalService.getSapLoginTypeByPersonalId(personalId, page, size);
       return ResponseEntity.ok(sapLoginTypePage);
    }


    @GetMapping(value = "/as-pic/by-partner-id/{partnerExternal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity, PersonalEntity>>> getPersonalAsPartnerPIC(
            @PathVariable Long partnerExternal,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            PaginationUtil<PersonalEntity, PersonalEntity> response = personalService.getPersonalAsPartnerPIC(partnerExternal, page, size);
            return new HttpResponseDTO<>(response, HttpStatus.OK)
                    .setResponseHeaders("partnerExternal", partnerExternal)
                    .toResponse();
        } catch (ResponseStatusException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
