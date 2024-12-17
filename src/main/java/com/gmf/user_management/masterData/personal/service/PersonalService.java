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

public interface PersonalService {
    PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException;
    PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    PaginationUtil<PersonalEntity, PersonalEntity> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO);
    PersonalResponDTO getPersonalById(Long idPersonal) throws NotFoundException, JsonProcessingException;
    PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws NotFoundException, JsonProcessingException;
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalByPartnerId(Long partnerExternal, Integer page, Integer size, PersonalRequestDTO requestDTO)throws NotFoundException;
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalByDinas(String dinas, Integer page, Integer size) throws NotFoundException, JsonProcessingException;
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalAsPartnerPIC(Long partnerExternal, Integer page, Integer size)throws NotFoundException;
    String countUIDByDinas();
}
