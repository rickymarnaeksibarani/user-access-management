package com.gmf.user_management.modules.unitJobCode;

import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.jobCode.repositories.JobCodeRepository;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
import com.gmf.user_management.modules.unit.repository.UnitRepository;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodeDTO;
import com.gmf.user_management.modules.unitJobCode.dto.UnitJobCodeResponDTO;
import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import com.gmf.user_management.modules.unitJobCode.repository.UnitJobCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitJobCodeService {
    @Autowired
    private UnitJobCodeRepository unitJobCodeRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private JobCodeRepository jobCodeRepository;


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
//        if (allJobCode.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Code not found");

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

    public PaginationUtil<UnitJobCodeEntity, UnitJobCodeResponDTO> getAllJobCode(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page -1 ,size);
        Page<UnitJobCodeEntity> pages = unitJobCodeRepository.findAll(paging);
        return new PaginationUtil<>(pages, UnitJobCodeResponDTO.class);
    }

    public UnitJobCodeResponDTO getUnitJobCodeById(Long idUnitJobCode) {
        UnitJobCodeEntity unitJobCode = JpaResultHelperUtil.getSingleResultFromOptional(unitJobCodeRepository.findById(idUnitJobCode));
        if (unitJobCode == null)throw new ResponseStatusException(HttpStatus.NOT_FOUND, idUnitJobCode + " not found");
        UnitJobCodeResponDTO respone = ObjectMapperUtil.map(unitJobCode, UnitJobCodeResponDTO.class);
        respone.setJobCodeCount(unitJobCode.getJobCodeList() != null ? unitJobCode.getJobCodeList().size() : 0);
        respone.setUnitCount(unitJobCode.getUnitList()!= null ? unitJobCode.getUnitList().size() : 0);
        return respone;
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
