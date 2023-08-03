package com.gmf.user_management.domains.user;

import com.gmf.user_management.core.dto.EmployeeDTO;
import com.gmf.user_management.core.enums.HashEnum;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.core.utils.PasswordUtil;
import com.gmf.user_management.domains.user.dto.UserActiveDTO;
import com.gmf.user_management.domains.user.dto.UserDTO;
import com.gmf.user_management.domains.user.dto.UserLoginDTO;
import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import com.gmf.user_management.domains.user.entities.UserEntity;
import com.gmf.user_management.domains.user.entities.UserLoginEntity;
import com.gmf.user_management.domains.user.repositories.UserActiveMainRepository;
import com.gmf.user_management.domains.user.repositories.UserLoginMainRepository;
import com.gmf.user_management.domains.user.repositories.UserMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserActiveMainRepository userActiveMainRepository;

    @Autowired
    private UserMainRepository userMainRepository;

    @Autowired
    private UserLoginMainRepository userLoginMainRepository;
    @Override
    public PaginationUtil<UserActiveEntity, UserActiveDTO> getUserPaginated(Integer page, Integer perPage, UserPaginationRequest userPaginationRequest) {
        Pageable paging = PageRequest.of(page - 1, perPage);

        Specification<UserActiveEntity> specs = Specification
                .where(UserPredicate.likeEmail(userPaginationRequest.getEmail()))
                .and(UserPredicate.equalSourceId(userPaginationRequest.getUserSourceId()))
                .and(UserPredicate.equalSourceId(userPaginationRequest.getUserSourceId()))
                .and(UserPredicate.searchTerm(userPaginationRequest.getSearchTerm()))
                ;

        Page<UserActiveEntity> pagedUsers = userActiveMainRepository.findAll(specs, paging);

        return new PaginationUtil<>(pagedUsers, UserActiveDTO.class);
    }

    @Override
    public UserActiveDTO getDetailUserById(Long userId) throws NotFoundException {
        UserActiveEntity user = JpaResultHelperUtil.getSingleResultFromOptional(userActiveMainRepository.findById(userId));

        if(user == null) {
            throw new NotFoundException("User Not Found");
        }

        return ObjectMapperUtil.map(user, UserActiveDTO.class);
    }

    @Override
    public EmployeeDTO getDetailUserByEmployeeNumber(String personalNumber) throws NotFoundException {

        Specification<UserActiveEntity> specs = Specification
                .where(UserPredicate.equalUsername(personalNumber))
                ;

        List<UserActiveEntity> users = userActiveMainRepository.findAll(specs);

        if(users.isEmpty()) {
            throw new NotFoundException("Employee Not Found");
        }

        UserActiveEntity user = users.get(0);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setPersonalName((user.getFirstName() + " " + user.getLastName()).trim());
        employeeDTO.setPersonalNumber(user.getUsername());
        // TODO: Please use a proper Image
        employeeDTO.setPersonalImage("https://raw.githubusercontent.com/antoniosai/gmf-assets/master/blank-avatar.png");
        employeeDTO.setPersonalUnit(user.getWorkstation());
        employeeDTO.setIsGmfEmployee(false);
        employeeDTO.setPersonalEmail(user.getEmail());

        return employeeDTO;
    }

    @Override
    public UserActiveDTO getDetailUserByPersonalNumber(String personalNumber) throws NotFoundException {
        Specification<UserActiveEntity> specs = Specification
                .where(UserPredicate.equalUsername(personalNumber))
                ;

        List<UserActiveEntity> users = userActiveMainRepository.findAll(specs);

        if(users.isEmpty()) {
            throw new NotFoundException("Employee Not Found");
        }

        UserActiveEntity user = users.get(0);

        return ObjectMapperUtil.map(user, UserActiveDTO.class);
    }

    @Override
    public UserActiveDTO updateUserDetailById(Long userId, UserDTO userDTO) throws NotFoundException {

        if(!userMainRepository.existsById(userId)) {
            throw new NotFoundException("User with ID => " + userId + " is not Found");
        }

        userDTO.setIdUserDetail(userId);

        UserEntity user = userMainRepository.saveAndFlush(ObjectMapperUtil.map(userDTO, UserEntity.class));

        UserActiveEntity userActive = JpaResultHelperUtil.getSingleResultFromOptional(userActiveMainRepository.findById(user.getIdUserDetail()));

        return ObjectMapperUtil.map(userActive, UserActiveDTO.class);
    }

    @Override
    public UserActiveDTO updateUserLoginById(Long userLoginId, UserLoginDTO userLoginDTO) throws NotFoundException {
        if(!userLoginMainRepository.existsById(userLoginId)) {
            throw new NotFoundException("User Login with ID => "+ userLoginId + " is not found!");
        }

        userLoginDTO.setIdActiveUser(userLoginId);
        userLoginDTO.setPassword(PasswordUtil.generatePassword(userLoginDTO.getPassword(), HashEnum.SHA1.getDisplayName()));

        UserLoginEntity userLoginEntity = userLoginMainRepository.saveAndFlush(ObjectMapperUtil.map(userLoginDTO, UserLoginEntity.class));

        return getDetailUserById(userLoginEntity.getUserDetailId());
    }

    @Override
    public UserActiveDTO createNewUser(UserDTO userDTO) {
        UserEntity user = userMainRepository.saveAndFlush(ObjectMapperUtil.map(userDTO, UserEntity.class));

        UserActiveEntity userActive = JpaResultHelperUtil.getSingleResultFromOptional(userActiveMainRepository.findById(user.getIdUserDetail()));

        return ObjectMapperUtil.map(userActive, UserActiveDTO.class);
    }

    @Override
    @Transactional
    public UserActiveDTO createNewUserLogin(Long userId, UserLoginDTO userLoginDTO) throws NotFoundException {

        if(!userMainRepository.existsById(userId)) {
            throw new NotFoundException("User with ID => " + userId + " is not exist");
        }

        userLoginDTO.setUserDetailId(userId);
        userLoginDTO.setPassword(PasswordUtil.generatePassword(userLoginDTO.getPassword(), HashEnum.SHA1.getDisplayName()));
        userLoginMainRepository.saveAndFlush(ObjectMapperUtil.map(userLoginDTO, UserLoginEntity.class));

        return ObjectMapperUtil.map(JpaResultHelperUtil.getSingleResultFromOptional(userActiveMainRepository.findById(userId)), UserActiveDTO.class);

    }

    @Override
    @Transactional
    public Boolean removeUserLoginById(Long userLoginId) {
        userLoginMainRepository.deleteById(userLoginId);

        return true;
    }

    @Override
    public Boolean removeUserDetailById(Long userId) {

        userMainRepository.deleteById(userId);

        return true;
    }
}
