package com.gmf.user_management.masterData.sapLoginType.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeDTO;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeResponDTO;
import com.gmf.user_management.masterData.sapLoginType.entities.SapLoginTypeEntity;
import org.springframework.http.ResponseEntity;

public interface SapLoginTypeController {
    ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>>createSapLoginType (SapLoginTypeDTO requestDto);
    ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>>updateSapLoginType (Long idSapLoginType, SapLoginTypeDTO requestDto) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Boolean>>deleteSapLoginType(Long idSapLoginType)throws NotFoundException;
    ResponseEntity<HttpResponseDTO<PaginationUtil<SapLoginTypeEntity, SapLoginTypeResponDTO[]>>>getAllSapLoginType(Integer page, Integer size, SapLoginTypeDTO requestDTO);
    ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>> getSapLoginTypeById(Long idSapLoginType)throws NotFoundException;
}
