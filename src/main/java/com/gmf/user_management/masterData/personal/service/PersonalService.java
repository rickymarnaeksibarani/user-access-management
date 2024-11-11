package com.gmf.user_management.masterData.personal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalRequestDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;

public interface PersonalService {
    PersonalResponDTO createPersonal(PersonalDTO request) throws JsonProcessingException;
    PersonalResponDTO updatePersonal(Long idPersonal, PersonalDTO request) throws NotFoundException;
    PaginationUtil<PersonalEntity, PersonalResponDTO> getAllPersonal(Integer page, Integer size, PersonalRequestDTO requestDTO);
    PersonalResponDTO getPersonalById(Long idPersonal)throws NotFoundException;
    PersonalResponDTO getPersonalByPersonalNumber(String personalNumber)throws NotFoundException;
    PersonalResponDTO getPersonalByPartnerId(String partnerId)throws NotFoundException;
    PersonalResponDTO getPersonalByDinas(String dinas)throws NotFoundException;
    PersonalResponDTO getPersonalAsPartnerPIC(Integer parntnerId)throws NotFoundException;
    Long countUIDByDinas(String dinas);
}
