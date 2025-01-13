package com.gmf.user_management.masterData.unitJobCode;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.unitJobCode.dto.UnitJobCodeDTO;
import com.gmf.user_management.masterData.unitJobCode.dto.UnitJobCodeResponDTO;
import com.gmf.user_management.masterData.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/unit-jobCode")
public class UnitJobCodeController {
    @Autowired
    private UnitJobCodeService unitJobCodeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UnitJobCodeResponDTO>> createUnitJobCode(
            @RequestBody @Valid UnitJobCodeDTO request
    ){
        UnitJobCodeResponDTO response = unitJobCodeService.createUnitJobCode(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_unit_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UnitJobCodeResponDTO>>updateUnitJobCode(
            @RequestBody @Valid UnitJobCodeDTO request,
            @PathVariable Long id_unit_job_code
    ) {
        UnitJobCodeResponDTO responDto = unitJobCodeService.updatedUnit(id_unit_job_code, request);
        return new HttpResponseDTO<>(responDto,HttpStatus.OK)
                .setResponseHeaders("responDto", responDto)
                .toResponse();
    }

    @DeleteMapping(value = "/by-id/{id_unit_job_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteUnitJobCode(
            @PathVariable Long id_unit_job_code
    ){
        return new HttpResponseDTO<>(unitJobCodeService.deleteUnitJobCode(id_unit_job_code))
                .setResponseHeaders("idUnitJobCode", id_unit_job_code)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllJobCode(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Object allDataUnitJobCode = unitJobCodeService.getAllJobCode(page, size);
        return new HttpResponseDTO<>(allDataUnitJobCode, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .toResponse();
    }

    @GetMapping(value = "/by-id/{idUnitJobCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UnitJobCodeResponDTO>>getUnitJobCodeById(
            @PathVariable @IsNumeric @IsRequired Long idUnitJobCode
    ){
        return new HttpResponseDTO<>(unitJobCodeService.getUnitJobCodeById(idUnitJobCode))
                .setResponseHeaders("idUnitJobCode", idUnitJobCode)
                .toResponse();
    }

    @GetMapping(value = "/job-codes/by-unit/{unitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity>>> getJobCodeIdByUnitId(
            @PathVariable Long unitId,
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ) {
        PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity> jobCodeIds = unitJobCodeService.getJobCodeIdByUnitId(unitId, page, size);
        return new HttpResponseDTO<>(jobCodeIds, HttpStatus.OK)
                .setResponseHeaders("unitId", unitId)
                .toResponse();
    }

    @GetMapping(value = "/unit-ids/by-job-code/{jobCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity>>> getUnitIdsByJobCodeId(
            @PathVariable Long jobCodeId,
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ){
        PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity> unitIds = unitJobCodeService.getUnitIdByJobCodeId(jobCodeId, page, size);
        return new HttpResponseDTO<>(unitIds, HttpStatus.OK)
                .setResponseHeaders("unitIds", unitIds)
                .toResponse();
    }
    //todo: countJobCodesByUnitId
    @GetMapping("/count-job-code-by-unit/{unitId}")
    public ResponseEntity<Long> countJobCodeByUnitId(@PathVariable Long unitId) {
        try {
            Long jobCodeCount = unitJobCodeService.countJobCodeByUnitId(unitId);
            return ResponseEntity.ok(jobCodeCount);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to count JobCodes for Unit ID: " + unitId, e);
        }
    }
}
