package com.gmf.user_management.modules.unit;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.modules.unit.dto.UnitDTO;
import com.gmf.user_management.modules.unit.dto.UnitRequestDto;
import com.gmf.user_management.modules.unit.dto.UnitResponDto;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UnitResponDto>> createUnit(
            @RequestBody @Valid UnitDTO request
    ) throws Exception {
        UnitResponDto response = unitService.createUnit(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_unit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UnitResponDto>>updateUnit(
            @RequestBody @Valid UnitDTO request,
            @PathVariable Long id_unit
    )throws Exception{
        UnitResponDto responDto = unitService.updatedUnit(id_unit, request);
        return new HttpResponseDTO<>(responDto,HttpStatus.OK)
                .setResponseHeaders("responDto", responDto)
                .toResponse();
    }

    @DeleteMapping(value = "/by-id/{id_unit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteUnit(
            @PathVariable Long id_unit
    ){
        return new HttpResponseDTO<>(unitService.deleteUnit(id_unit))
                .setResponseHeaders("idUnit", id_unit)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllUnit(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            UnitRequestDto requestDto
    ){
        Object allDataUnit = unitService.getAllUnit(page, size, requestDto);
        return new HttpResponseDTO<>(allDataUnit, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }

    @GetMapping("/by-id/{idUnit}")
    public ResponseEntity<HttpResponseDTO<UnitResponDto>>getUnitById(
            @PathVariable @IsNumeric @IsRequired Long idUnit
    ){
        return new HttpResponseDTO<>(unitService.getUnitById(idUnit))
                .setResponseHeaders("idUnit", idUnit)
                .toResponse();
    }

    @GetMapping(value = "/by-business-unit-code/{businessUnitCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UnitEntity, UnitEntity>>> getUnitByBusinessUnitCodeId(
            @PathVariable Long businessUnitCodeId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PaginationUtil<UnitEntity, UnitEntity> units = unitService.getUnitByBusinessUnitCodeId(businessUnitCodeId, page, size);
        return new HttpResponseDTO<>(units, HttpStatus.OK)
                .setResponseHeaders("businessUnitCodeId", businessUnitCodeId)
                .toResponse();
    }
}
