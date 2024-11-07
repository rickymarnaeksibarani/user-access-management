package com.gmf.user_management.masterData.composite;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleRequestDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleResponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/composite")
public class CompositeRoleController {

    @Autowired
    private CompositeRoleService compositeRoleService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>> createCompositeRole(
            @RequestPart @Valid CompositeRoleDTO request
    ) throws Exception {
        CompositeRoleResponDTO response = compositeRoleService.createCompositeRole(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{idCompositeRole}")
    public  ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>> updateCompositeRole(
            @RequestPart @Valid CompositeRoleDTO request,
            @PathVariable Long idCompositeRole
    )throws Exception{
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
            @RequestParam(defaultValue = "20") Integer size,
            CompositeRoleRequestDTO requestDto
    ){
        Object allComposite = compositeRoleService.getAllCompositeRole(page, size, requestDto);
        return new HttpResponseDTO<>(allComposite, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .setResponseHeaders("requestDto", requestDto)
                .toResponse();
    }

    @GetMapping("/by-id/{id_composite_role}")
    public ResponseEntity<HttpResponseDTO<CompositeRoleResponDTO>>getCompositeRoleById(
            @PathVariable @IsNumeric @IsRequired Long id_composite_role
    ) throws NotFoundException {
        return new HttpResponseDTO<>(compositeRoleService.getCompositeRoleById(id_composite_role), HttpStatus.OK)
                .setResponseHeaders("idCompositeRole", id_composite_role)
                .toResponse();
    }

    @GetMapping("/count-by-job-code/{jobCodeId}")
    public ResponseEntity<HttpResponseDTO<Integer>> countCompositeRoleByJobCodeId(
            @PathVariable Long jobCodeId
    ) {
        int count = compositeRoleService.countCompositeRoleByJobCodeId(jobCodeId);
        return new HttpResponseDTO<>(count, HttpStatus.OK)
                .setResponseHeaders("jobCodeId", jobCodeId)
                .toResponse();
    }


    @GetMapping("/by-job-code/{jobCodeId}")
    public ResponseEntity<HttpResponseDTO<List<CompositeRoleResponDTO>>> getCompositeRoleByJobCodeId(
            @PathVariable Long jobCodeId
    ) {
        List<CompositeRoleResponDTO> response = compositeRoleService.getCompositeRoleByJobCodeId(jobCodeId);
        return new HttpResponseDTO<>(response, HttpStatus.OK)
                .setResponseHeaders("jobCodeId", jobCodeId)
                .toResponse();
    }

}
