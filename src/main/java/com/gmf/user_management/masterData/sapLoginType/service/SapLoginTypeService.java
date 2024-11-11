package com.gmf.user_management.masterData.sapLoginType.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeDTO;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeResponDTO;
import com.gmf.user_management.masterData.sapLoginType.entities.SapLoginTypeEntity;

public interface SapLoginTypeService {
    SapLoginTypeResponDTO createSapLoginType (SapLoginTypeDTO requestDto);
    SapLoginTypeResponDTO updateSapLoginType (Long idSapLoginType, SapLoginTypeDTO requestDto) throws NotFoundException;
    Boolean deleteSapLoginType(Long idSapLoginType)throws NotFoundException;
    PaginationUtil<SapLoginTypeEntity, SapLoginTypeResponDTO> getAllSapLoginType(Integer page, Integer size, SapLoginTypeDTO requestDTO);
    SapLoginTypeResponDTO getSapLoginTypeById(Long idSapLoginType)throws NotFoundException;


}
