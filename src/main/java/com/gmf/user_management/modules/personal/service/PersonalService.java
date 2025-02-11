package com.gmf.user_management.modules.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.personal.dto.PersonalDTO;
import com.gmf.user_management.modules.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.modules.personal.dto.PersonalResponDTO;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface PersonalService {
    PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException;
    PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    PaginationUtil<PersonalEntity, PersonalEntity> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO);
    PersonalResponDTO getPersonalById(Long idPersonal) throws NotFoundException, JsonProcessingException;
    PersonalResponDTO getPersonalByPersonalNumber(String personalNumber) throws NotFoundException, JsonProcessingException;
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalByPartnerId(Long partnerExternal, Integer page, Integer size, PersonalRequestDTO requestDTO)throws NotFoundException;
    PaginationUtil<PersonalResponDTO, PersonalResponDTO> getAllPersonalByDinasWithUid(Integer page, Integer size,String dinas, PersonalRequestDTO requestDTO);
    PaginationUtil<PersonalResponDTO, PersonalResponDTO> getAllPersonalWithUid(Integer page, Integer size, PersonalRequestDTO requestDto);
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalAsPartnerPIC(Long partnerExternal, Integer page, Integer size)throws NotFoundException;
    PaginationUtil<PersonalEntity, PersonalEntity> getPersonalBySapLoginTypeId(Long sapLoginTypeId, Integer page, Integer size);
    PaginationUtil<SapLoginTypeEntity, SapLoginTypeEntity> getSapLoginTypeByPersonalId(Long personalId, Integer page, Integer size);
    String countUIDByDinas();
    Map<String, Long> countPersonalByLicense();
//    Map<String, Long> countLicenseNameByPersonal();
}
