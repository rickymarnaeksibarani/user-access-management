package com.gmf.user_management.modules.usersAccessDomain.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PasswordUtil;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.personal.repository.PersonalRepository;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainResponDTO;
import com.gmf.user_management.modules.usersAccessDomain.entities.UserAccessDomainEntity;
import com.gmf.user_management.modules.usersAccessDomain.repository.UADRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class UserAccessDomainServiceImpl implements UserAccessDomainService {
    @Autowired
    private UADRepository uadRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccessDomainResponDTO userAccessDomainResponDTO(UserAccessDomainEntity userAccessDomainEntity){
        return UserAccessDomainResponDTO.builder()
                .idUserAccessDomain(userAccessDomainEntity.getIdUserAccessDomain())
                .personalList(userAccessDomainEntity.getPersonalList())
                .isNetworkAccess(userAccessDomainEntity.getIsNetworkAccess())
                .isDomainAccess(userAccessDomainEntity.getIsDomainAccess())
                .username(userAccessDomainEntity.getUsername())
                .password(userAccessDomainEntity.getPassword())
                .createdAt(userAccessDomainEntity.getCreatedAt())
                .createdBy(userAccessDomainEntity.getCreatedBy())
                .updatedAt(userAccessDomainEntity.getUpdatedAt())
                .updatedBy(userAccessDomainEntity.getUpdatedBy())
                .build();
    }
    @Override
    public UserAccessDomainResponDTO createUserAccessDomain(UserAccessDomainDTO request) {
        UserAccessDomainEntity uad = new UserAccessDomainEntity();
        UserAccessDomainEntity payload = uadPayload(request, uad);
        uadRepository.save(payload);
        return userAccessDomainResponDTO(payload);
    }
    //todo: when create, get by-id, update the profile picture is null. please fix that!

    @Override
    public UserAccessDomainResponDTO updateUserAccessDomain(Long idUserAccessDomain, UserAccessDomainDTO request) throws NotFoundException {
        UserAccessDomainEntity data = uadRepository.findById(idUserAccessDomain).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        UserAccessDomainEntity payload = uadPayload(request, data);
        uadRepository.saveAndFlush(payload);
        return userAccessDomainResponDTO(payload);
    }

    @Override
    public Boolean deleteUserAccessDomain(Long idUser) {
        uadRepository.deleteById(idUser);
        return true;
    }

    @Override
    public UserAccessDomainResponDTO getUserAccessDomainByPersonalId(Long personalId, UserAccessDomainDTO request){
            PersonalEntity personal = personalRepository.findById(personalId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal ID not found"));
            UserAccessDomainEntity userAccessDomainEntity = uadRepository.findByPersonalListContaining(personal)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Access Domain not found for the given Personal ID"));
            return userAccessDomainResponDTO(userAccessDomainEntity);
        }


    private UserAccessDomainEntity uadPayload(UserAccessDomainDTO userAccessDomainDTO, UserAccessDomainEntity userAccessDomainEntity){
        List<PersonalEntity> allPersonal = personalRepository.findByIdPersonalIsIn(userAccessDomainDTO.getPersonalList());
        if (allPersonal.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal not found");
        userAccessDomainEntity.setPersonalList(allPersonal);
        userAccessDomainEntity.setIsDomainAccess(userAccessDomainDTO.getIsDomainAccess());
        userAccessDomainEntity.setIsNetworkAccess(userAccessDomainDTO.getIsNetworkAccess());
        userAccessDomainEntity.setUsername(userAccessDomainDTO.getUsername());
        userAccessDomainEntity.setPassword(PasswordUtil.generatePassword(userAccessDomainDTO.getPassword(), passwordEncoder));
        userAccessDomainEntity.setCreatedBy(userAccessDomainDTO.getCreatedBy());
        userAccessDomainEntity.setUpdatedBy(userAccessDomainDTO.getUpdatedBy());
        return userAccessDomainEntity;
    }
}
