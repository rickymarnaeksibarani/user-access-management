package com.gmf.user_management.modules.userLicense.service;

import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.modules.applicationLicense.repository.ApplicationLicenseRepository;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.personal.repository.PersonalRepository;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseDTO;
import com.gmf.user_management.modules.userLicense.dto.UserLicensePredicateDto;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseRequestDto;
import com.gmf.user_management.modules.userLicense.dto.UserLicenseResponeDTO;
import com.gmf.user_management.modules.userLicense.entities.UserLicenseEntity;
import com.gmf.user_management.modules.userLicense.repository.UserLicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLicenseServiceImpl implements UserLicenseService{

    private final UserLicenseRepository userLicenseRepository;
    private final ApplicationLicenseRepository applicationLicenseRepository;
    private final PersonalRepository personalRepository;


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
//    @Cacheable(value = "updateUserLicense")
    public UserLicenseResponeDTO updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto){
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
    public Boolean deleteUserLicense(Long idUserLicense){
        userLicenseRepository.deleteById(idUserLicense);
        return true;
    }

    @Override
//    @Cacheable("personalIdByApplicationLicenseId")
    public PaginationUtil<UserLicenseEntity, UserLicenseEntity> getPersonalIdByApplicationLicenseId(Long applicationLicenseId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page-1, size);
        Page<UserLicenseEntity> userLicenseEntities = userLicenseRepository.findByApplicationLicenseList_IdApplicationLicense(applicationLicenseId, paging);
        if (userLicenseEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user licenses found for the given application license ID");
        }
        return new PaginationUtil<>(userLicenseEntities, UserLicenseEntity.class);
    }

    @Override
//    @Cacheable("applicationLicenseIdByUserId")
    public PaginationUtil<UserLicenseEntity, UserLicenseEntity> getApplicationLicenseIdByUserId(Long idUserLicense, Integer page, Integer size) {
        Pageable pages = PageRequest.of(page-1, size);
        Page<UserLicenseEntity> userLicenseEntities = userLicenseRepository.findByPersonalList_IdPersonal(idUserLicense, pages);
        if (userLicenseEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user licenses found for the given user ID");
        }
        return new PaginationUtil<>(userLicenseEntities, UserLicenseEntity.class);
    }

    private UserLicenseEntity userLicensePayload(UserLicenseDTO userLicenseDTO, UserLicenseEntity userLicenseEntity){
        List<ApplicationLicenseEntity> allApplication = applicationLicenseRepository.findByIdApplicationLicenseIsIn(userLicenseDTO.getApplicationLicenseList());
//        if (allApplication.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        List<PersonalEntity>allPersonal = personalRepository.findByIdPersonalIsIn(userLicenseDTO.getPersonalList());
        if (allPersonal.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal not found");
        userLicenseEntity.setApplicationLicenseList(allApplication);
        userLicenseEntity.setPersonalList(allPersonal);
        userLicenseEntity.setCreatedBy(userLicenseDTO.getCreatedBy());
        userLicenseEntity.setUpdatedBy(userLicenseDTO.getUpdatedBy());
        return userLicenseEntity;
    }

    @Override
//    @Cacheable(value = "userLicense", sync = true)
    public PaginationUtil<UserLicenseEntity, UserLicenseEntity> getAllUserLicense(Integer page, Integer size, UserLicenseRequestDto requestDTO) {

        Specification<UserLicenseEntity> spec = Specification
                .where(UserLicensePredicateDto.hasApplicationLicense())
                .and(UserLicensePredicateDto.filterByPersonalName(requestDTO.getFilterByPersonalName()))
                .and(UserLicensePredicateDto.filterByDinas(requestDTO.getFilterByDinas()))
                .and(UserLicensePredicateDto.filterByUnit(requestDTO.getFilterByUnit()))
                .and(UserLicensePredicateDto.filterByPersonalNumber(requestDTO.getFilterByPersonalNumber()))
                .and(UserLicensePredicateDto.filterByPartner(requestDTO.getFilterByPartner()))
                .and(UserLicensePredicateDto.filterByPassCardNumber(requestDTO.getFilterByPassCardNumber()))
                .and(UserLicensePredicateDto.applicationId(requestDTO.getApplicationId()));

        Pageable paging = PageRequest.of(page - 1, size);
        Page<UserLicenseEntity> userLicensePage = userLicenseRepository.findAll(spec, paging);
        return new PaginationUtil<>(userLicensePage, UserLicenseEntity.class);
    }

    @Override
    public Map<String, Long> countPersonalApplicationsByDinas(String dinas) {
        List<UserLicenseEntity> allLicenses = userLicenseRepository.findAll();

        return allLicenses.stream()
                .flatMap(userLicense -> userLicense.getPersonalList().stream()
                        .filter(personal -> dinas == null || personal.getDinas().equalsIgnoreCase(dinas))
                        .flatMap(personal -> userLicense.getApplicationLicenseList().stream()
                                .map(application -> Map.entry(personal.getDinas(), application))))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()));
    }

    @Override
    public String countTotalApplicationLicenses() {
        long totalApplication =  userLicenseRepository.findAll().stream()
                .mapToLong(userLicense -> userLicense.getApplicationLicenseList().size())
                .sum();
        return "Total Application: " + totalApplication;
    }

}
