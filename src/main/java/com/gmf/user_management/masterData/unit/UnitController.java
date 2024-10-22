package com.gmf.user_management.masterData.unit;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.masterData.unit.dto.UnitDTO;
import com.gmf.user_management.masterData.unit.dto.UnitResponDto;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<UnitResponDto>> createUnit(
            @RequestPart @Valid UnitDTO request
    ) throws Exception {
        log.info("request {}", request);
        UnitResponDto response = unitService.createUnit(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }
}
