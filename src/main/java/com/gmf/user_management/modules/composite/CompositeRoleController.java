package com.gmf.user_management.modules.composite;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleDTO;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleRequestDTO;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleResponDTO;
import com.gmf.user_management.modules.composite.compositeEntities.CompositeRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/composite")
public class CompositeRoleController {

    @Autowired
    private CompositeRoleService compositeRoleService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>> createCompositeRole(
            @RequestBody @Valid CompositeRoleDTO request
    ) {
        CompositeRoleResponDTO response = compositeRoleService.createCompositeRole(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{idCompositeRole}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>> updateCompositeRole(
            @RequestBody @Valid CompositeRoleDTO request,
            @PathVariable Long idCompositeRole
    ) {
        CompositeRoleResponDTO responDTO = compositeRoleService.updateCompositeRole(idCompositeRole, request);
        return new HttpResponseDTO<>(responDTO,HttpStatus.OK).setResponseHeaders("responDTO", responDTO).toResponse();
    }

    @DeleteMapping(value = "/by-id/{idCompositeRole}")
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteCompositeRole(
            @PathVariable Long idCompositeRole
    ){
        return new HttpResponseDTO<>(compositeRoleService.deleteCompositeRole(idCompositeRole))
                .setResponseHeaders("idCompositeRole", idCompositeRole)
                .toResponse();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>>getAllCompositeRole(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            CompositeRoleRequestDTO requestDto
    ){
        Object allComposite = compositeRoleService.getAllCompositeRole(page, size, requestDto);
        return new HttpResponseDTO<>(allComposite, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }

    @GetMapping(value = "/by-id/{id_composite_role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>>getCompositeRoleById(
            @PathVariable @IsNumeric @IsRequired Long id_composite_role
    ) throws NotFoundException {
        return new HttpResponseDTO<>(compositeRoleService.getCompositeRoleById(id_composite_role), HttpStatus.OK)
                .setResponseHeaders("idCompositeRole", id_composite_role)
                .toResponse();
    }

    @GetMapping(value = "/by-job-code/{jobCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<CompositeRoleEntity, CompositeRoleEntity>>> getCompositeRoleByJobCodeId(
            @PathVariable Long jobCodeId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PaginationUtil<CompositeRoleEntity, CompositeRoleEntity> response = compositeRoleService.getCompositeRoleByJobCodeId(jobCodeId, page, size);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("jobCodeId", jobCodeId)
                .toResponse();
    }

    @GetMapping(value = "/count-by-job-code/{jobCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> countCompositeRoleByJobCodeId(
            @PathVariable @IsNumeric @IsRequired Long jobCodeId
    ) {
        Long count = compositeRoleService.countCompositeRoleByJobCodeId(jobCodeId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("response", count);
        response.put("headers", Map.of(
                "status", HttpStatus.OK.name(),
                "jobCodeId", jobCodeId,
                "totalCompositeRoleByJobCode", count
        ));
        response.put("message", null);
        response.put("time", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}

