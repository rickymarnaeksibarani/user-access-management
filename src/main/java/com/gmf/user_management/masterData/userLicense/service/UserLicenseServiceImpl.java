package com.gmf.user_management.masterData.userLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.applicationLicense.repository.ApplicationLicenseRepository;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.personal.repository.PersonalRepository;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.masterData.userLicense.dto.UserLicenseResponeDTO;
import com.gmf.user_management.masterData.userLicense.entities.UserLicenseEntity;
import com.gmf.user_management.masterData.userLicense.repository.UserLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserLicenseServiceImpl implements UserLicenseService{
    @Autowired
    private UserLicenseRepository userLicenseRepository;
    @Autowired
    private ApplicationLicenseRepository applicationLicenseRepository;
    @Autowired
    private PersonalRepository personalRepository;

    private UserLicenseResponeDTO userLicenseResponeDTO(UserLicenseEntity userLicenseEntity){
        return UserLicenseResponeDTO.builder()
                .idUserLicense(userLicenseEntity.getIdUserLicense())
                .applicationLicenseList(userLicenseEntity.getApplicationLicenseList())
                .personalList(userLicenseEntity.getPersonalList())
                .createdBy(userLicenseEntity.getCreatedBy())
                .updatedBy(userLicenseEntity.getUpdatedBy())
                .createdAt(userLicenseEntity.getCreatedAt())
                .updatedAt(userLicenseEntity.getUpdatedAt())
                .build();
    }
    @Override
    public UserLicenseResponeDTO createUserLicense(UserLicenseDTO requestDto) {
        try {
            UserLicenseEntity data = new UserLicenseEntity();
            UserLicenseEntity payload = userLicensePayload(requestDto, data);
            userLicenseRepository.save(payload);
            return userLicenseResponeDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserLicenseResponeDTO updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto) throws NotFoundException {
        try {
            UserLicenseEntity data = userLicenseRepository.findById(idUserLicense).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Data User License not found"));
            UserLicenseEntity payload = userLicensePayload(requestDto, data);
            userLicenseRepository.saveAndFlush(payload);
            return userLicenseResponeDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteUserLicense(Long idUserLicense) throws NotFoundException {
        userLicenseRepository.deleteById(idUserLicense);
        return true;
    }

    @Override
    public UserLicenseResponeDTO[] getPersonalIdByApplicationLicenseId(Long applicationLicenseId) {
        // TODO: 17/12/2024 : filter application name, licenseType, searchByName 
        // TODO: 17/12/2024 : pagination 
        List<UserLicenseEntity> userLicenseEntities = userLicenseRepository.findByApplicationLicenseList_IdApplicationLicense(applicationLicenseId);
        if (userLicenseEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user licenses found for the given application license ID");
        }
        return userLicenseEntities.stream()
                .map(this::userLicenseResponeDTO)
                .toArray(UserLicenseResponeDTO[]::new);
    }

    @Override
    public UserLicenseResponeDTO[] getApplicationLicenseIdByUserId(Long idUserLicense) {
        List<UserLicenseEntity> userLicenseEntities = userLicenseRepository.findByPersonalList_IdPersonal(idUserLicense);
        if (userLicenseEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user licenses found for the given user ID");
        }
        return userLicenseEntities.stream()
                .map(this::userLicenseResponeDTO)
                .toArray(UserLicenseResponeDTO[]::new);
    }


    private UserLicenseEntity userLicensePayload(UserLicenseDTO userLicenseDTO, UserLicenseEntity userLicenseEntity){
        List<ApplicationLicenseEntity> allApplication = applicationLicenseRepository.findByIdApplicationLicenseIsIn(userLicenseDTO.getApplicationLicenseList());
        if (allApplication.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        List<PersonalEntity>allPersonal = personalRepository.findByIdPersonalIsIn(userLicenseDTO.getPersonalList());
        if (allPersonal.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal not found");
        userLicenseEntity.setApplicationLicenseList(allApplication);
        userLicenseEntity.setPersonalList(allPersonal);
        userLicenseEntity.setCreatedBy(userLicenseDTO.getCreatedBy());
        userLicenseEntity.setUpdatedBy(userLicenseDTO.getUpdatedBy());
        return userLicenseEntity;
    }
}
