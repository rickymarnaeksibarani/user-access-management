package com.gmf.user_management.modules.unitJobCode;

import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.jobCode.repositories.JobCodeRepository;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
import com.gmf.user_management.modules.unit.repository.UnitRepository;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodeDTO;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodePredicate;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodeRequestDTO;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodeResponDTO;
import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import com.gmf.user_management.modules.unitJobCode.repository.UnitJobCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitJobCodeService {

    private final UnitJobCodeRepository unitJobCodeRepository;
    private final UnitRepository unitRepository;
    private final JobCodeRepository jobCodeRepository;

    private UnitJobCodeResponDTO unitJobCodeResponDTO(UnitJobCodeEntity unitJobCodeEntity){
        return UnitJobCodeResponDTO.builder()
                .idUnitJobCode(unitJobCodeEntity.getIdUnitJobCode())
                .unitList(unitJobCodeEntity.getUnitList())
                .jobCodeList(unitJobCodeEntity.getJobCodeList())
                .createdAt(unitJobCodeEntity.getCreatedAt())
                .createdBy(unitJobCodeEntity.getCreatedBy())
                .updatedAt(unitJobCodeEntity.getUpdatedAt())
                .updatedBy(unitJobCodeEntity.getUpdatedBy())
                .jobCodeCount((unitJobCodeEntity.getJobCodeList() != null ? unitJobCodeEntity.getJobCodeList().size() : 0))
                .unitCount((unitJobCodeEntity.getUnitList()!=null ? unitJobCodeEntity.getUnitList().size() : 0))
                .build();
    }

    public UnitJobCodeResponDTO createUnitJobCode(UnitJobCodeDTO request) {
        try {
            UnitJobCodeEntity data = new UnitJobCodeEntity();
            UnitJobCodeEntity payload = unitJobCodePayload(request, data);
            unitJobCodeRepository.save(payload);
            return unitJobCodeResponDTO(payload);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public UnitJobCodeResponDTO updatedUnit(Long idUnitJobCode, UnitJobCodeDTO request) {
        try {
            UnitJobCodeEntity data = unitJobCodeRepository.findById(idUnitJobCode).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Unit Job Code not found"));
            UnitJobCodeEntity payload = unitJobCodePayload(request, data);
            unitJobCodeRepository.saveAndFlush(payload);
            return unitJobCodeResponDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private  UnitJobCodeEntity unitJobCodePayload(UnitJobCodeDTO unitJobCodeDTO, UnitJobCodeEntity unitJobCodeEntity){
        List<UnitEntity> allUnit = unitRepository.findByIdUnitIsIn(unitJobCodeDTO.getUnitList());
        if (allUnit.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found");

        List<JobCodeEntity> allJobCode = jobCodeRepository.findByIdJobCodeIsIn(unitJobCodeDTO.getJobCodeList());
        unitJobCodeEntity.setUnitList(allUnit);
        unitJobCodeEntity.setJobCodeList(allJobCode);
        unitJobCodeEntity.setCreatedBy(unitJobCodeDTO.getCreatedBy());
        unitJobCodeEntity.setUpdatedBy(unitJobCodeDTO.getUpdatedBy());
        return unitJobCodeEntity;
    }


    public Boolean deleteUnitJobCode(Long idUnitJobCode) {
        unitJobCodeRepository.deleteById(idUnitJobCode);
        return true;
    }

    public PaginationUtil<UnitJobCodeEntity, UnitJobCodeResponDTO> getAllJobCode(Integer page, Integer size, UnitJobCodeRequestDTO requestDTO) {
        Pageable paging = PageRequest.of(page -1 ,size);
        Specification<UnitJobCodeEntity> spec = Specification
                .where(UnitJobCodePredicate.filterByUnit(requestDTO.getFilterByUnit()))
                .and(UnitJobCodePredicate.filterByJobCode(requestDTO.getFilterByJobCode()))
                .and(UnitJobCodePredicate.filterByDinas(requestDTO.getFilterByDinas()));
        Page<UnitJobCodeEntity> pages = unitJobCodeRepository.findAll(spec,paging);
        return new PaginationUtil<>(pages, UnitJobCodeResponDTO.class);
    }

    public UnitJobCodeResponDTO getUnitJobCodeById(Long idUnitJobCode, String filterJobCode) {
        UnitJobCodeEntity unitJobCode = JpaResultHelperUtil.getSingleResultFromOptional(unitJobCodeRepository.findById(idUnitJobCode));
        if (unitJobCode == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, idUnitJobCode + " not found");

        UnitJobCodeResponDTO response = ObjectMapperUtil.map(unitJobCode, UnitJobCodeResponDTO.class);
        if (filterJobCode != null && !filterJobCode.isEmpty()) {
            List<JobCodeEntity> filteredJobCodes = unitJobCode.getJobCodeList().stream()
                    .filter(jobCode -> jobCode.getJobCode().equalsIgnoreCase(filterJobCode))
                    .toList();
            response.setJobCodeList(filteredJobCodes);
            response.setJobCodeCount(filteredJobCodes.size());
        } else {
            response.setJobCodeCount(unitJobCode.getJobCodeList() != null ? unitJobCode.getJobCodeList().size() : 0);
        }
        response.setUnitCount(unitJobCode.getUnitList() != null ? unitJobCode.getUnitList().size() : 0);
        return response;
    }



    public PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity> getJobCodeIdByUnitId(Long unitId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<UnitJobCodeEntity> pages = unitJobCodeRepository.findByUnitList_IdUnit(unitId, paging);
        return new PaginationUtil<>(pages, UnitJobCodeEntity.class);
    }


    public PaginationUtil<UnitJobCodeEntity, UnitJobCodeEntity> getUnitIdByJobCodeId(Long jobCodeId, Integer page, Integer size){
        Pageable paging = PageRequest.of(page -1, size);
        Page<UnitJobCodeEntity>pages = unitJobCodeRepository.findByJobCodeList_IdJobCode(jobCodeId, paging);
        return new PaginationUtil<>(pages, UnitJobCodeEntity.class);
    }

    public Long countJobCodeByUnitId(Long unitId) {
        try {
            List<UnitJobCodeEntity> unitJobCodeEntities = unitJobCodeRepository.findByUnitList_IdUnit(unitId);
            return unitJobCodeEntities.stream()
                    .mapToLong(entity -> entity.getJobCodeList().size())
                    .sum();
        } catch (Exception e) {
            throw new RuntimeException("Failed to count JobCodes for Unit ID: " + unitId, e);
        }
    }


}
