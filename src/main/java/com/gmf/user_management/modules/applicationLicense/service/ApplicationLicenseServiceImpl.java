package com.gmf.user_management.modules.applicationLicense.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseDTO;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicensePredicate;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseRequest;
import com.gmf.user_management.modules.applicationLicense.dto.ApplicationLicenseResponDTO;
import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.modules.applicationLicense.repository.ApplicationLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.TransactionalException;

@Service
@RequiredArgsConstructor
public class ApplicationLicenseServiceImpl implements ApplicationLicenseService{

    private final ApplicationLicenseRepository applicationLicenseRepository;

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
            boolean exists =  applicationLicenseRepository.existsByApplicationNameAndLicenseType(request.getApplicationName(), request.getLicenseType());
            if (exists){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "License Type "+ request.getLicenseType() + " is already used in " + request.getApplicationName());
            }
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
            ApplicationLicenseEntity data = applicationLicenseRepository.findById(idApplicationLicense).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ID with: " + idApplicationLicense + " is not found"));
            ApplicationLicenseEntity payload = applicationLicensePayload(request, data);
            applicationLicenseRepository.saveAndFlush(payload);
            return applicationLicenseResponDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteLicense(Long idApplicationLicense) {
        try {
            applicationLicenseRepository.deleteById(idApplicationLicense);
            return true;
        } catch (TransactionalException e) {
            throw new RuntimeException("Transaction failed while deleting the Application License.");
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete: Application License is still linked to another record.");
        }
    }

    @Override
    public PaginationUtil<ApplicationLicenseEntity, ApplicationLicenseResponDTO> getAllLicense(
            Integer page, Integer size, ApplicationLicenseRequest requestDto
    ){
        Pageable paging = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdAt")));
        Specification<ApplicationLicenseEntity> specs = Specification
                .where(ApplicationLicensePredicate.searchTerm(requestDto.getSearchTerm()))
                .and(ApplicationLicensePredicate.activeStatus(requestDto.getActiveStatus()));

        Page<ApplicationLicenseEntity> pages = applicationLicenseRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, ApplicationLicenseResponDTO.class);
    }

    @Override
    public ApplicationLicenseResponDTO getApplicationLicenseById(Long applicationLicenseId) throws NotFoundException {
        ApplicationLicenseEntity applicationLicenses = JpaResultHelperUtil.getSingleResultFromOptional(applicationLicenseRepository.findById(applicationLicenseId));
        if (applicationLicenses == null){
            throw new NotFoundException("id with: " + applicationLicenseId + " is not found");
        }
        return ObjectMapperUtil.map(applicationLicenses, ApplicationLicenseResponDTO.class);
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
        if ("Lifetime".equalsIgnoreCase(String.valueOf(applicationLicenseDTO.getLicenseCategory()))){
            applicationLicenseEntity.setExpiredDate(null);
        } else if ("Subscription".equalsIgnoreCase(String.valueOf(applicationLicenseDTO.getLicenseCategory()))) {
            if (applicationLicenseDTO.getExpiredDate() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired Date must be provided for Subscription License");
            }
            applicationLicenseEntity.setExpiredDate(applicationLicenseDTO.getExpiredDate());

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid License category");
        }
        return applicationLicenseEntity;
    }
}
