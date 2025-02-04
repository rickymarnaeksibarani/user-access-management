package com.gmf.user_management.modules.userLicense.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
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

//    @Override
//    public UserLicenseResponeDTO updateUserLicense(Long idUserLicense, UserLicenseDTO requestDto) throws NotFoundException {
//        try {
//            UserLicenseEntity data = userLicenseRepository.findById(idUserLicense)
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data User License not found"));
//
//            updateAllowedFields(data, requestDto);
//
//            userLicenseRepository.saveAndFlush(data);
//            return userLicenseResponeDTO(data);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private void updateAllowedFields(UserLicenseEntity data, UserLicenseDTO requestDto) {
//        if (requestDto.getApplicationLicenseList() != null) {
//            List<ApplicationLicenseEntity> allApplication = applicationLicenseRepository.findByIdApplicationLicenseIsIn(requestDto.getApplicationLicenseList());
//            if (allApplication.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicaton License not found");
//            data.setApplicationLicenseList(allApplication);
//        }
//        if (requestDto.getPersonalList() != null){
//            List<PersonalEntity> allPersonal = personalRepository.findByIdPersonalIsIn(requestDto.getPersonalList());
//            if (allPersonal.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal not found");
//            data.setPersonalList(allPersonal);
//        }
//        if (requestDto.getCreatedBy() != null) data.setCreatedBy(requestDto.getCreatedBy());
//        if (requestDto.getUpdatedBy() != null) data.setUpdatedBy(requestDto.getUpdatedBy());
//    }

    @Override
    public Boolean deleteUserLicense(Long idUserLicense) throws NotFoundException {
        userLicenseRepository.deleteById(idUserLicense);
        return true;
    }

    @Override
    public PaginationUtil<UserLicenseEntity, UserLicenseEntity> getPersonalIdByApplicationLicenseId(Long applicationLicenseId, Integer page, Integer size) {
        // TODO: 17/12/2024 : filter application name, licenseType, searchByName 
        Pageable paging = PageRequest.of(page-1, size);
        Page<UserLicenseEntity> userLicenseEntities = userLicenseRepository.findByApplicationLicenseList_IdApplicationLicense(applicationLicenseId, paging);
        if (userLicenseEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user licenses found for the given application license ID");
        }
        return new PaginationUtil<>(userLicenseEntities, UserLicenseEntity.class);
    }

    @Override
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

//    @Override
//    public PaginationUtil<UserLicenseEntity, UserLicenseEntity>getAllUserLicense(Integer page, Integer size, UserLicenseRequestDto requestDTO){
//        Pageable paging = PageRequest.of(page-1, size);
//        Specification<UserLicenseEntity> specification = Specification
//                .where(UserLicensePredicateDto.filterByApplicationName(requestDTO.getApplicationName()))
//                .and(UserLicensePredicateDto.filterByPersonalName(requestDTO.getPersonalName()));
//
//        Page<UserLicenseEntity> userLicensePage = userLicenseRepository.findAll(specification, paging);
//        return new PaginationUtil<>(userLicensePage, UserLicenseEntity.class);
//    }

    //todo > create new endpoint to get all userLicense. filter > dinas, application name, personal name, company name, unit, personal number, passcard number
    @Override
    public PaginationUtil<UserLicenseEntity, UserLicenseEntity> getAllUserLicense(Integer page, Integer size, UserLicenseRequestDto requestDTO) {
        Pageable paging = PageRequest.of(page - 1, size);

        Specification<UserLicenseEntity> spec = Specification.where(null);

        if (requestDTO.getFilterByPersonalName() != null && !requestDTO.getFilterByPersonalName().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByPersonalName(requestDTO.getFilterByPersonalName()));
        }

        if (requestDTO.getFilterByDinas() != null && !requestDTO.getFilterByDinas().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByDinas(requestDTO.getFilterByDinas()));
        }

        if (requestDTO.getFilterByUnit() != null && !requestDTO.getFilterByUnit().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByUnit(requestDTO.getFilterByUnit()));
        }

        if (requestDTO.getFilterByPartner() != null && !requestDTO.getFilterByPartner().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByPartner(requestDTO.getFilterByPartner()));
        }

        if (requestDTO.getFilterByPersonalNumber() != null && !requestDTO.getFilterByPersonalNumber().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByPersonalNumber(requestDTO.getFilterByPersonalNumber()));
        }
        if (requestDTO.getFilterByPassCardNumber() != null && !requestDTO.getFilterByPassCardNumber().isEmpty()) {
            spec = spec.and(UserLicensePredicateDto.filterByPassCardNumber(requestDTO.getFilterByPassCardNumber()));
        }
//
//        if (requestDTO.getFilterByApplicationName() != null && !requestDTO.getFilterByApplicationName().isEmpty()) {
//            spec = spec.and(UserLicensePredicateDto.filterByApplicationName(requestDTO.getFilterByApplicationName()));
//        }

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
