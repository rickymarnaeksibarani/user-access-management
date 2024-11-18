package com.gmf.user_management.masterData.businessUnitCode;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.DataSourceService;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
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
        if (businessUnitCodeEntity.getContractId() != null){
            try {
                contractDetails = dataSourceService.getContractById(businessUnitCodeEntity.getContractId());
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
        if (request.getContractId() !=null){
            businessUnitCodeEntity.setContractId(request.getContractId());
        }
        return businessUnitCodeEntity;
    }
    // TODO: 18/11/2024 > Get Data Business Unit Code by Id fixing contractId

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
