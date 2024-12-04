package com.gmf.user_management.masterData.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PersonalService {
    PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException;
    PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    PaginationUtil<PersonalEntity, PersonalEntity> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO)throws JsonProcessingException;
    PersonalResponDTO getPersonalById(Long idPersonal) throws NotFoundException, JsonProcessingException;
    PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws NotFoundException, JsonProcessingException;
    List<PersonalResponDTO> getPersonalByPartnerId(Long partnerId)throws NotFoundException;
    List<PersonalResponDTO> getPersonalByDinas(String dinas) throws NotFoundException, JsonProcessingException;
    PersonalResponDTO getPersonalAsPartnerPIC(Integer parntnerId)throws NotFoundException;
    String countUIDByDinas();
}
