package com.gmf.user_management.masterData.personal;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalDTO;
import com.gmf.user_management.masterData.personal.dto.PersonalResponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/v1/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @PostMapping
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>> createPersonal(
            @RequestPart @Valid PersonalDTO request,
            @RequestPart(value = "personalPicture", required = false) List<MultipartFile> personalPicture
    ) throws Exception {
        if (Objects.nonNull(personalPicture)){
            request.setPersonalPicture(personalPicture);
        }
        PersonalResponDTO response = personalService.createPersonal(request);
        return new HttpResponseDTO<>(response, HttpStatus.CREATED)
                .setResponseHeaders("request", response)
                .toResponse();
    }

    @PutMapping(value = "/by-id/{id_personal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PersonalResponDTO>>updatePersonal(
            @RequestPart @Valid PersonalDTO request,
            @PathVariable Long id_personal,
            @RequestPart(value = "personalPicture", required = false) List<MultipartFile> personalPicture
    )throws Exception{
        if (Objects.nonNull(personalPicture)) {
            request.setPersonalPicture(personalPicture);
        }
        PersonalResponDTO responDto = personalService.updatePersonal(id_personal, request);
        return new HttpResponseDTO<>(responDto,HttpStatus.OK)
                .setResponseHeaders("responDto", responDto)
                .toResponse();
    }

    @DeleteMapping(value = "/by-id/{id_personal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Boolean>> deletePersonal(
            @PathVariable Long id_personal
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new HttpResponseDTO<>(personalService.deletePersonal(id_personal))
                .setResponseHeaders("id_personal", id_personal)
                .toResponse();
    }
}
