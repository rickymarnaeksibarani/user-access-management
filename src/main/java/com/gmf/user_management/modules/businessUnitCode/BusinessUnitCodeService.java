package com.gmf.user_management.modules.businessUnitCode;

import com.gmf.user_management.config.multipleDataSourceConfiguration.service.DataSourceService;
import com.gmf.user_management.config.multipleDataSourceConfiguration.repository.ExternalRepository;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodePredicate;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeRequestDto;
import com.gmf.user_management.modules.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.modules.businessUnitCode.repositories.BusinessUnitCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class BusinessUnitCodeService {
    @Autowired
    private BusinessUnitCodeRepository businessUnitCodeRepository;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private ExternalRepository externalRepository;

    private BusinessUnitCodeResponDTO businessRespone(BusinessUnitCodeEntity businessUnitCodeEntity) {
        Map<String, Object> partnerExternal = null;
        if (businessUnitCodeEntity.getPartnerExternal() != null){
            try {
                partnerExternal = dataSourceService.getContractById(businessUnitCodeEntity.getPartnerExternal());
            }catch (ResponseStatusException e){
                partnerExternal = Map.of("error", Objects.requireNonNull(e.getReason()));
            }
        }
        return BusinessUnitCodeResponDTO.builder()
                .idBusinessUnitCode(businessUnitCodeEntity.getIdBusinessUnitCode())
                .businessUnitCode(businessUnitCodeEntity.getBusinessUnitCode())
                .partnerExternal(partnerExternal)
                .description(businessUnitCodeEntity.getDescription())
                .dinas(businessUnitCodeEntity.getDinas())
                .createdAt(businessUnitCodeEntity.getCreatedAt())
                .createdBy(businessUnitCodeEntity.getCreatedBy())
                .updatedAt(businessUnitCodeEntity.getUpdatedAt())
                .updatedBy(businessUnitCodeEntity.getUpdatedBy())
                .build();
    }

    public BusinessUnitCodeResponDTO createBusinessUnitCode(BusinessUnitCodeDTO request){
        Map<String, Object> exPartner = externalRepository.findContractById(request.getPartnerExternal());
        if (exPartner.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");

        BusinessUnitCodeEntity businessUnitCode = new BusinessUnitCodeEntity();
        BusinessUnitCodeEntity payload = businessUnitCodePayload(request, businessUnitCode);
        payload.setPartnerExternal((Long) exPartner.get("id"));
        payload.setPartnerName((String) exPartner.get("name"));
        businessUnitCodeRepository.save(payload);
        return businessRespone(payload);
    }

    public BusinessUnitCodeResponDTO updateBusinessUnitCode(Long id_business_unit_code, BusinessUnitCodeDTO request){
        BusinessUnitCodeEntity businessUnitCode = businessUnitCodeRepository.findById(id_business_unit_code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        BusinessUnitCodeEntity payload = businessUnitCodePayload(request, businessUnitCode);
        businessUnitCodeRepository.saveAndFlush(payload);
        return businessRespone(payload);
    }

    public Boolean deleteBusinessUnitCode(Long idBusinessUnitCode) {
        businessUnitCodeRepository.deleteById(idBusinessUnitCode);
        return true;
    }
    private BusinessUnitCodeEntity businessUnitCodePayload(BusinessUnitCodeDTO request, BusinessUnitCodeEntity businessUnitCodeEntity) {
        businessUnitCodeEntity.setBusinessUnitCode(request.getBusinessUnitCode());
        businessUnitCodeEntity.setDescription(request.getDescription());
        businessUnitCodeEntity.setDinas(request.getDinas());
        businessUnitCodeEntity.setCreatedBy(request.getCreatedBy());
        businessUnitCodeEntity.setUpdatedBy(request.getUpdatedBy());
        if (request.getPartnerExternal() !=null){
            businessUnitCodeEntity.setPartnerExternal(request.getPartnerExternal());
            businessUnitCodeEntity.setPartnerName(request.getPartnerName());
        }
        return businessUnitCodeEntity;
    }

    public PaginationUtil<BusinessUnitCodeEntity, BusinessUnitCodeEntity> getAllBusinessUnitCode(Integer page, Integer size, BusinessUnitCodeRequestDto requestDto) {
        try {
            Pageable paging = PageRequest.of(page -1, size);
            Specification<BusinessUnitCodeEntity> specification = Specification
                    .where(BusinessUnitCodePredicate.searchTerm(requestDto.getSearchTerm()))
                    .and(BusinessUnitCodePredicate.dinas(requestDto.getDinas()))
                    .and(BusinessUnitCodePredicate.searchNamePartner(requestDto.getPartnerName()));
            Page<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findAll(specification, paging);
            return new PaginationUtil<>(businessUnitCodes, BusinessUnitCodeEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving all Business Unit Codes", e);
        }
    }

    public BusinessUnitCodeResponDTO getBusinessUnitCodeById(Long idBusinessUnitCode){
        try {
            BusinessUnitCodeEntity businessUnitCodeEntity = businessUnitCodeRepository.findById(idBusinessUnitCode)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
            return businessRespone(businessUnitCodeEntity);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving Business Unit Code by ID", e);
        }
    }

    public PaginationUtil<BusinessUnitCodeEntity, BusinessUnitCodeEntity> getBusinessUnitCodeByDinas(String dinas, Integer page, Integer size) {
        try {
            Pageable paging = PageRequest.of(page-1, size);
            Page<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findByDinas(dinas, paging);
            if (businessUnitCodes.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No business unit codes found for the given dinas");
            }

            return new PaginationUtil<>(businessUnitCodes, BusinessUnitCodeEntity.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}


