package com.gmf.user_management.domains.user;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.domains.user.dto.UserActiveDTO;
import com.gmf.user_management.domains.user.dto.UserDTO;
import com.gmf.user_management.domains.user.dto.UserLoginDTO;
import com.gmf.user_management.domains.user.entities.UserActiveEntity;

public interface UserService {
    PaginationUtil<UserActiveEntity, UserActiveDTO> getUserPaginated(Integer page, Integer perPage, UserPaginationRequest userPaginationRequest);

    UserActiveDTO getDetailUserById(Long userId) throws NotFoundException;

    UserActiveDTO updateUserDetailById(Long userId, UserDTO userDTO) throws NotFoundException;

    UserActiveDTO updateUserLoginById(Long userLoginId, UserLoginDTO userLoginDTO) throws NotFoundException;

    UserActiveDTO createNewUser(UserDTO userDTO);

    UserActiveDTO createNewUserLogin(Long userId, UserLoginDTO userLoginDTO) throws NotFoundException;

    Boolean removeUserLoginById(Long userLoginId);
}
