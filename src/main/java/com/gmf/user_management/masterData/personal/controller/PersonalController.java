package com.gmf.user_management.masterData.personal.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.http.ResponseEntity;

public interface PersonalController {
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>createPersonal(PersonalDTO request);
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>updatePersonal(Long idPersonal, PersonalDTO request)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PaginationUtil<PersonalEntity, PersonalResponDTO>>>getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO);
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalById(Long idPersonal)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalByPersonalNumber(String personalNumber)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalByPartnerId(String partnerId)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalByDinas(String dinas)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PersonalResponDTO>>getPersonalAsPartnerPIC(Integer parntnerId)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Long>>countUIDByDinas(String dinas);
}
