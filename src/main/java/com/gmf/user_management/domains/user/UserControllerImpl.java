package com.gmf.user_management.domains.user;

import com.gmf.user_management.core.dto.EmployeeDTO;
import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.validations.IsNumeric;
import com.gmf.user_management.core.validations.IsRequired;
import com.gmf.user_management.domains.user.dto.UserActiveDTO;
import com.gmf.user_management.domains.user.dto.UserDTO;
import com.gmf.user_management.domains.user.dto.UserLoginDTO;
import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
@Validated
@Slf4j
public class UserControllerImpl {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<HttpResponseDTO<PaginationUtil<UserActiveEntity, UserActiveDTO>>> getPaginatedUser(
        @RequestParam(defaultValue = "1") @IsNumeric String page,
        @RequestParam(defaultValue = "20") @IsNumeric String perPage,
        UserPaginationRequest userPaginationRequest
    ) {
        return new HttpResponseDTO<>(userService.getUserPaginated(Integer.parseInt(page), Integer.parseInt(perPage), userPaginationRequest), HttpStatus.OK)
            .setResponseHeaders("page", page)
            .setResponseHeaders("perPage", perPage)
            .setResponseHeaders("userPaginationRequest", userPaginationRequest)
            .toResponse();
    }

    @GetMapping("/{personalNumber}")
    public ResponseEntity<HttpResponseDTO<EmployeeDTO>> getDetailUserByPersonalNumber(
            @PathVariable @IsNumeric @IsRequired String personalNumber
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userService.getDetailUserByEmployeeNumber(personalNumber), HttpStatus.OK)
            .setResponseHeaders("personalNumber", personalNumber)
            .toResponse();
    }

    @GetMapping("/by-id/{personalNumber}")
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> getDetailUserById(
            @PathVariable @IsNumeric @IsRequired String personalNumber
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userService.getDetailUserById(Long.parseLong(personalNumber)), HttpStatus.OK)
                .setResponseHeaders("personalNumber", personalNumber)
                .toResponse();
    }

    @GetMapping("/by-personal-number/{personalNumber}")
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> getDetailUserByEmployeeNumber(
            @PathVariable @IsNumeric @IsRequired String personalNumber
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userService.getDetailUserByPersonalNumber(personalNumber), HttpStatus.OK)
                .setResponseHeaders("personalNumber", personalNumber)
                .toResponse();
    }

    @PostMapping
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> createNewUser(
        @Valid @RequestBody UserDTO userDTO
    ) {
        return new HttpResponseDTO<>(userService.createNewUser(userDTO), HttpStatus.CREATED)
            .setResponseHeaders("userDTO", userDTO)
            .toResponse();
    }

    @PostMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> addNewLoginToExistingUserById(
        @PathVariable @IsRequired @IsNumeric String userId,
        @RequestBody @Valid UserLoginDTO userLoginDTO
    ) throws NotFoundException {

        userLoginDTO.setUserDetailId(Long.parseLong(userId));

        return new HttpResponseDTO<>(userService.createNewUserLogin(Long.parseLong(userId), userLoginDTO), HttpStatus.CREATED)
            .setResponseHeaders("userId", userId)
            .setResponseHeaders("userLoginDTO", userLoginDTO)
            .toResponse();
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> updateUserById(
            @PathVariable @IsRequired @IsNumeric String userId,
            @Valid @RequestBody UserDTO userDTO
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userService.updateUserDetailById(Long.parseLong(userId), userDTO), HttpStatus.CREATED)
                .setResponseHeaders("userDTO", userDTO)
                .toResponse();
    }


    @PutMapping(value = "/{userLoginId}/update-login", produces = "application/json")
    public ResponseEntity<HttpResponseDTO<UserActiveDTO>> updateUserLoginById(
        @PathVariable @IsRequired @IsNumeric String userLoginId,
        @RequestBody @Valid UserLoginDTO userLoginDTO
    ) throws NotFoundException {
        return new HttpResponseDTO<>(userService.updateUserLoginById(Long.parseLong(userLoginId), userLoginDTO), HttpStatus.CREATED)
            .setResponseHeaders("userLoginId", userLoginId)
            .setResponseHeaders("userLoginDTO", userLoginDTO)
            .toResponse();
    }

    @DeleteMapping(value = "/{userLoginId}/remove-login", produces = "application/json")
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteUserLoginById(
        @PathVariable @IsRequired @IsNumeric String userLoginId
    ) {
        return new HttpResponseDTO<>(userService.removeUserLoginById(Long.parseLong(userLoginId)), HttpStatus.OK)
            .setResponseHeaders("userLoginId", userLoginId)
            .toResponse();
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<HttpResponseDTO<Boolean>> deleteUserById(
            @PathVariable @IsRequired @IsNumeric String userId
    ) {
        return new HttpResponseDTO<>(userService.removeUserDetailById(Long.parseLong(userId)), HttpStatus.NO_CONTENT)
                .setResponseHeaders("userId", userId)
                .toResponse();
    }
}
