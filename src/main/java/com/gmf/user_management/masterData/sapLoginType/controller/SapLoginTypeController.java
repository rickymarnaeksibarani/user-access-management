package com.gmf.user_management.masterData.sapLoginType.controller;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeDTO;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeRequest;
import com.gmf.user_management.masterData.sapLoginType.dto.SapLoginTypeResponDTO;
import com.gmf.user_management.masterData.sapLoginType.service.SapLoginTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/sapLoginType")
public class SapLoginTypeController {
    @Autowired
    private SapLoginTypeServiceImpl sapLoginTypeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>> createSapLoginType(
            @RequestBody @Valid SapLoginTypeDTO request
    ){
        SapLoginTypeResponDTO response = sapLoginTypeService.createSapLoginType(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{idSapLoginType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>> updateSapLoginType(
            @PathVariable Long idSapLoginType,
            @RequestBody @Valid SapLoginTypeDTO request
    ) throws Exception {
        SapLoginTypeResponDTO response = sapLoginTypeService.updateSapLoginType(idSapLoginType, request);
        return new HttpResponseDTO<>(response,HttpStatus.OK)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value ="/by-id/{idSapLoginType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteSapLoginType(
            @PathVariable Long idSapLoginType
    ) throws NotFoundException {
        return new HttpResponseDTO<>(sapLoginTypeService.deleteSapLoginType(idSapLoginType))
                .setResponseHeaders("idSapLoginType", idSapLoginType)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllSapLoginType(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            SapLoginTypeRequest requestDto
    ){
        Object allData = sapLoginTypeService.getAllSapLoginType(page, size, requestDto);
        return new HttpResponseDTO<>(allData, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }


    @GetMapping("/by-id/{idSapLoginType}")
    public ResponseEntity<HttpResponseDTO<SapLoginTypeResponDTO>> getSapLoginTypeById(
            @PathVariable @IsNumeric @IsRequired Long idSapLoginType
    )throws NotFoundException {
        return new HttpResponseDTO<>(sapLoginTypeService.getSapLoginTypeById(idSapLoginType), HttpStatus.OK)
                .setResponseHeaders("idSapLoginType", idSapLoginType)
                .toResponse();
    }
}
