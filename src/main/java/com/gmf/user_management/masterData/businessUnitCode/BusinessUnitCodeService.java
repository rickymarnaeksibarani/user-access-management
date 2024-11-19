package com.gmf.user_management.masterData.businessUnitCode;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.DataSourceService;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeDTO;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.businessUnitCode.repositories.BusinessUnitCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BusinessUnitCodeService {
    @Autowired
    private BusinessUnitCodeRepository businessUnitCodeRepository;
    @Autowired
    private DataSourceService dataSourceService;

    private BusinessUnitCodeResponDTO businessRespone(BusinessUnitCodeEntity businessUnitCodeEntity) {
        Map<String, Object> contractDetails = null;
        if (businessUnitCodeEntity.getPartnerId() != null){
            try {
                contractDetails = dataSourceService.getContractById(businessUnitCodeEntity.getPartnerId());
            }catch (ResponseStatusException e){
                contractDetails = Map.of("error", Objects.requireNonNull(e.getReason()));
            }
        }
        return BusinessUnitCodeResponDTO.builder()
                .idBusinessUnitCode(businessUnitCodeEntity.getIdBusinessUnitCode())
                .businessUnitCode(businessUnitCodeEntity.getBusinessUnitCode())
                .contractDetails(contractDetails)
                .description(businessUnitCodeEntity.getDescription())
                .dinas(businessUnitCodeEntity.getDinas())
                .createdAt(businessUnitCodeEntity.getCreatedAt())
                .createdBy(businessUnitCodeEntity.getCreatedBy())
                .updatedAt(businessUnitCodeEntity.getUpdatedAt())
                .updatedBy(businessUnitCodeEntity.getUpdatedBy())
                .build();
    }

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
        businessUnitCodeRepository.deleteById(idBusinessUnitCode);
        return true;
    }
    private BusinessUnitCodeEntity businessUnitCodePayload(BusinessUnitCodeDTO request, BusinessUnitCodeEntity businessUnitCodeEntity) {
        businessUnitCodeEntity.setBusinessUnitCode(request.getBusinessUnitCode());
        businessUnitCodeEntity.setDescription(request.getDescription());
        businessUnitCodeEntity.setDinas(request.getDinas());
        businessUnitCodeEntity.setCreatedBy(request.getCreatedBy());
        businessUnitCodeEntity.setUpdatedBy(request.getUpdatedBy());
        if (request.getPartnerId() !=null){
            businessUnitCodeEntity.setPartnerId(request.getPartnerId());
        }
        return businessUnitCodeEntity;
    }

    public BusinessUnitCodeResponDTO[] getAllBusinessUnitCode() {
        try {
            List<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findAll();
            return businessUnitCodes.stream()
                    .map(this::businessRespone)
                    .toArray(BusinessUnitCodeResponDTO[]::new);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public BusinessUnitCodeResponDTO getBusinessUnitCodeById(Long idBusinessUnitCode) throws NotFoundException {
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

    public List<BusinessUnitCodeResponDTO> getBusinessUnitCodeByDinas(String dinas) {
        try {
            List<BusinessUnitCodeEntity> businessUnitCodes = businessUnitCodeRepository.findByDinas(dinas);
            if (businessUnitCodes.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No business unit codes found for the given dinas");
            }

            return businessUnitCodes.stream()
                    .map(this::businessRespone)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
