package com.gmf.user_management.masterData.businessUnitCode;

import com.gmf.user_management.core.ResourceNotFoundException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodePredicate;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeRequestDto;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.businessUnitCode.repositories.BusinessUnitCodeRepository;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessUnitCodeService {
    @Autowired
    private BusinessUnitCodeRepository businessUnitCodeRepository;

//    @Transactional("postgresTransactionManager")
    private BusinessUnitCodeResponDTO businessRespone(BusinessUnitCodeEntity businessUnitCodeEntity) {
        return BusinessUnitCodeResponDTO.builder()
                .idBusinessUnitCode(businessUnitCodeEntity.getIdBusinessUnitCode())
                .businessUnitCode(businessUnitCodeEntity.getBusinessUnitCode())
                .description(businessUnitCodeEntity.getDescription())
                .dinas(businessUnitCodeEntity.getDinas())
                .createdAt(businessUnitCodeEntity.getCreatedAt())
                .createdBy(businessUnitCodeEntity.getCreatedBy())
                .updatedAt(businessUnitCodeEntity.getUpdatedAt())
                .updatedBy(businessUnitCodeEntity.getUpdatedBy())
                .build();
    }

//    @Transactional("postgresTransactionManager")
    public BusinessUnitCodeResponDTO createBusinessUnitCode(BusinessUnitCodeDTO request){
        BusinessUnitCodeEntity businessUnitCode = new BusinessUnitCodeEntity();
        BusinessUnitCodeEntity payload = businessUnitCodePayload(request, businessUnitCode);
        businessUnitCodeRepository.save(payload);
        return businessRespone(payload);
    }

    public BusinessUnitCodeResponDTO updateBusinessUnitCode(Long id_business_unit_code, BusinessUnitCodeDTO request)throws Exception{
        BusinessUnitCodeEntity businessUnitCode = businessUnitCodeRepository.findById(id_business_unit_code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        BusinessUnitCodeEntity payload = businessUnitCodePayload(request, businessUnitCode);
        businessUnitCodeRepository.saveAndFlush(payload);
        return businessRespone(payload);
    }

    public Boolean deleteBusinessUnitCode(Long idBusinessUnitCode) {
        businessUnitCodeRepository.findById(idBusinessUnitCode);
        return true;
    }
    private BusinessUnitCodeEntity businessUnitCodePayload(BusinessUnitCodeDTO request, BusinessUnitCodeEntity businessUnitCodeEntity) {
        businessUnitCodeEntity.setBusinessUnitCode(request.getBusinessUnitCode());
        businessUnitCodeEntity.setDescription(request.getDescription());
        businessUnitCodeEntity.setDinas(request.getDinas());
        businessUnitCodeEntity.setCreatedBy(request.getCreatedBy());
        businessUnitCodeEntity.setUpdatedBy(request.getUpdatedBy());
        return businessUnitCodeEntity;
    }

//    public PaginationUtil<BusinessUnitCodeEntity, BusinessUnitCodeDTO> getAllBusinessUnitCode(
//            Integer page, Integer size, BusinessUnitCodeRequestDto requestDto
//    ){
//        if (requestDto == null || requestDto.getSearchTerm() == null || requestDto.getSearchTerm().isEmpty()) {
//            throw new ResourceNotFoundException("License Name not found");
//        }
//
//        Pageable paging = PageRequest.of(page -1, size);
//        Specification<BusinessUnitCodeEntity> specs = Specification
//                .where(BusinessUnitCodePredicate.searchTerm(requestDto.getSearchTerm()));
//        Page<BusinessUnitCodeEntity> pages = businessUnitCodeRepository.findAll(specs, paging);
//        return new PaginationUtil<>(pages, BusinessUnitCodeDTO.class);
//    }

    public BusinessUnitCodeResponDTO[] getAllBusinessUnitCode() {
        List<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findAll();
        return businessUnitCodes.stream()
                .map(this::businessRespone)
                .toArray(BusinessUnitCodeResponDTO[]::new);
    }


    public BusinessUnitCodeResponDTO getBusinessUnitCodeById(Long idBusinessUnitCode) throws NotFoundException {
        BusinessUnitCodeEntity businessUnitCode = JpaResultHelperUtil.getSingleResultFromOptional(businessUnitCodeRepository.findById(idBusinessUnitCode));
        if (businessUnitCode == null){
            throw new NotFoundException("id not found");
        }
        return ObjectMapperUtil.map(businessUnitCode, BusinessUnitCodeResponDTO.class);
    }

    public List<BusinessUnitCodeResponDTO> getBusinessUnitCodeByDinas(String dinas) {
        List<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findByDinas(dinas);
        if (businessUnitCodes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No business unit codes found for the given dinas");
        }

        // Map each BusinessUnitCodeEntity to BusinessUnitCodeResponDTO
        return businessUnitCodes.stream()
                .map(this::businessRespone)
                .collect(Collectors.toList());
    }

}
