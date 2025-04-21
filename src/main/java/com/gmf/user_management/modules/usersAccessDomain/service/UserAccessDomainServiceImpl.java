package com.gmf.user_management.modules.usersAccessDomain.service;

import com.gmf.user_management.core.utils.PasswordUtil;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.personal.repository.PersonalRepository;
import com.gmf.user_management.modules.usersAccessDomain.dto.PersonalDTOtoUAD;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainDTO;
import com.gmf.user_management.modules.usersAccessDomain.dto.UserAccessDomainResponDTO;
import com.gmf.user_management.modules.usersAccessDomain.entities.UserAccessDomainEntity;
import com.gmf.user_management.modules.usersAccessDomain.repository.UserAccessDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccessDomainServiceImpl implements UserAccessDomainService {

    private final UserAccessDomainRepository userAccessDomainRepository;
    private final PersonalRepository personalRepository;
    private final PasswordUtil passwordUtil;

    private UserAccessDomainResponDTO userAccessDomainResponDTO(UserAccessDomainEntity userAccessDomainEntity){
        PersonalEntity personal = userAccessDomainEntity.getPersonalId();
        PersonalDTOtoUAD personalDTO = new PersonalDTOtoUAD(
                personal.getIdPersonal(),
                personal.getPersonalName(),
                personal.getPersonalNumber(),
                personal.getEmail(),
                personal.getIdentityNumber(),
                personal.getIsPic(),
                personal.getActiveStatus()
        );
        return UserAccessDomainResponDTO.builder()
                .idUserAccessDomain(userAccessDomainEntity.getIdUserAccessDomain())
                .personal(personalDTO)
                .isNetworkAccess(userAccessDomainEntity.getIsNetworkAccess())
                .isDomainAccess(userAccessDomainEntity.getIsDomainAccess())
                .username(userAccessDomainEntity.getUsername())
                .createdAt(userAccessDomainEntity.getCreatedAt())
                .createdBy(userAccessDomainEntity.getCreatedBy())
                .updatedAt(userAccessDomainEntity.getUpdatedAt())
                .updatedBy(userAccessDomainEntity.getUpdatedBy())
                .build();
    }
    @Override
    public UserAccessDomainResponDTO createUserAccessDomain(UserAccessDomainDTO request) {
//        boolean exists = userAccessDomainRepository.existsByPersonalListIn(request.getPersonalList());
//        if (exists){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username: " + request.getUsername() + " is already used in "+ request.getPersonalList());
//        }
        UserAccessDomainEntity uad = new UserAccessDomainEntity();
        UserAccessDomainEntity payload = uadPayload(request, uad);
        userAccessDomainRepository.save(payload);
        return userAccessDomainResponDTO(payload);
    }

    @Override
    public UserAccessDomainResponDTO updateUserAccessDomain(Long idUserAccessDomain, UserAccessDomainDTO request){
        UserAccessDomainEntity data = userAccessDomainRepository.findById(idUserAccessDomain).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        UserAccessDomainEntity payload = uadPayload(request, data);
        userAccessDomainRepository.saveAndFlush(payload);
        return userAccessDomainResponDTO(payload);
    }

    @Override
    public Boolean deleteUserAccessDomain(Long idUser) {
        userAccessDomainRepository.deleteById(idUser);
        return true;
    }

    @Override
    public UserAccessDomainResponDTO getUserAccessDomainByPersonalId(Long personalId, UserAccessDomainDTO request){
            PersonalEntity personal = personalRepository.findById(personalId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal ID not found"));
            UserAccessDomainEntity userAccessDomainEntity = userAccessDomainRepository.findByPersonalId(personal)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Access Domain not found for the given Personal ID"));
            return userAccessDomainResponDTO(userAccessDomainEntity);
        }


    private UserAccessDomainEntity uadPayload(UserAccessDomainDTO userAccessDomainDTO, UserAccessDomainEntity userAccessDomainEntity){
        PersonalEntity personalId = personalRepository.findById(userAccessDomainDTO.getPersonalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Personal id with: " + userAccessDomainDTO.getPersonalId() + " is not found"));

        userAccessDomainRepository.findByPersonalId(personalId).ifPresent(existingEntity -> {
            if (userAccessDomainEntity.getIdUserAccessDomain() == null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Personal Id is already used");
            }
        });
        userAccessDomainEntity.setPersonalId(personalId);
        userAccessDomainEntity.setIsDomainAccess(userAccessDomainDTO.getIsDomainAccess());
        userAccessDomainEntity.setIsNetworkAccess(userAccessDomainDTO.getIsNetworkAccess());
        Optional.ofNullable(userAccessDomainDTO.getUsername()).ifPresent(userAccessDomainEntity::setUsername);

        if (userAccessDomainDTO.getPassword() != null && !userAccessDomainDTO.getPassword().trim().isEmpty()) {
            userAccessDomainEntity.setPassword(passwordUtil.generatePassword(userAccessDomainDTO.getPassword()));
        }

        return userAccessDomainEntity;
    }
}
