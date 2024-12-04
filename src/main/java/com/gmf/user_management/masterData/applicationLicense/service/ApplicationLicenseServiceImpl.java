package com.gmf.user_management.masterData.applicationLicense.service;

import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicensePredicate;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseRequest;
import com.gmf.user_management.masterData.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.applicationLicense.repository.ApplicationLicenseRepository;
import com.gmf.user_management.masterData.jobCode.dto.JobCodePredicate;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeRequestDto;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeResponeDTO;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class ApplicationLicenseServiceImpl implements ApplicationLicenseService{
    @Autowired
    ApplicationLicenseRepository applicationLicenseRepository;

    private ApplicationLicenseResponDTO applicationLicenseResponDTO(ApplicationLicenseEntity applicationLicenseEntity){
        return ApplicationLicenseResponDTO.builder()
                .idApplicationLicense(applicationLicenseEntity.getIdApplicationLicense())
                .applicationName(applicationLicenseEntity.getApplicationName())
                .licenseType(applicationLicenseEntity.getLicenseType())
                .quantity(applicationLicenseEntity.getQuantity())
                .licenseCategory(applicationLicenseEntity.getLicenseCategory())
                .expiredDate(applicationLicenseEntity.getExpiredDate())
                .activeStatus(applicationLicenseEntity.getActiveStatus())
                .createdAt(applicationLicenseEntity.getCreatedAt())
                .createdBy(applicationLicenseEntity.getCreatedBy())
                .updatedAt(applicationLicenseEntity.getUpdatedAt())
                .updatedBy(applicationLicenseEntity.getUpdatedBy())
                .build();
    }

    @Override
    public ApplicationLicenseResponDTO createLicense(ApplicationLicenseDTO request) {
        try {
            ApplicationLicenseEntity data = new ApplicationLicenseEntity();
            ApplicationLicenseEntity payload = applicationLicensePayload(request, data);
            applicationLicenseRepository.save(payload);
            return applicationLicenseResponDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApplicationLicenseResponDTO updateLicense(Long idApplicationLicense, ApplicationLicenseDTO request){
        try {
            ApplicationLicenseEntity data = applicationLicenseRepository.findById(idApplicationLicense).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
            ApplicationLicenseEntity payload = applicationLicensePayload(request, data);
            applicationLicenseRepository.saveAndFlush(payload);
            return applicationLicenseResponDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteLicense(Long idApplicationLicense) {
        applicationLicenseRepository.deleteById(idApplicationLicense);
        return true;
    }

    @Override
    public PaginationUtil<ApplicationLicenseEntity, ApplicationLicenseResponDTO> getAllLicense(
            Integer page, Integer size, ApplicationLicenseRequest requestDto
    ){
        Pageable paging = PageRequest.of(page - 1, size);
        Specification<ApplicationLicenseEntity> specs = Specification
                .where(ApplicationLicensePredicate.searchTerm(requestDto.getSearchTerm()))
                .and(ApplicationLicensePredicate.activeStatus(requestDto.getActiveStatus()));

        Page<ApplicationLicenseEntity> pages = applicationLicenseRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, ApplicationLicenseResponDTO.class);
    }

    private ApplicationLicenseEntity applicationLicensePayload(ApplicationLicenseDTO applicationLicenseDTO, ApplicationLicenseEntity applicationLicenseEntity){
        applicationLicenseEntity.setApplicationName(applicationLicenseDTO.getApplicationName());
        applicationLicenseEntity.setLicenseType(applicationLicenseDTO.getLicenseType());
        applicationLicenseEntity.setQuantity(applicationLicenseDTO.getQuantity());
        applicationLicenseEntity.setLicenseCategory(applicationLicenseDTO.getLicenseCategory());
        applicationLicenseEntity.setExpiredDate(applicationLicenseDTO.getExpiredDate());
        applicationLicenseEntity.setActiveStatus(applicationLicenseDTO.getActiveStatus());
        applicationLicenseEntity.setCreatedBy(applicationLicenseDTO.getCreatedBy());
        applicationLicenseEntity.setUpdatedBy(applicationLicenseDTO.getUpdatedBy());
        return applicationLicenseEntity;
    }
}
