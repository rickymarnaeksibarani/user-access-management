package com.gmf.user_management.domains.user;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.domains.user.dto.UserActiveDTO;
import com.gmf.user_management.domains.user.dto.UserDTO;
import com.gmf.user_management.domains.user.dto.UserLoginDTO;
import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<UserActiveEntity, UserActiveDTO>>> getPaginatedUser(String page, String perPage, UserPaginationRequest userPaginationRequest);
    ResponseEntity<HttpResponseDTO<UserActiveDTO>> getDetailUser(String userId) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<UserActiveDTO>> createNewUser(UserDTO userDTO);
    ResponseEntity<HttpResponseDTO<UserActiveDTO>> addNewLoginToExistingUserById(String userId, UserLoginDTO userLoginDTO) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<UserActiveDTO>> updateUserLoginById(String userLoginId, UserLoginDTO userLoginDTO) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<Boolean>> deleteUserLoginById(String userLoginId);
}
