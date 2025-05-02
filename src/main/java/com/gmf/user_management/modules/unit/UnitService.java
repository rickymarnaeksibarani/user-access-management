package com.gmf.user_management.modules.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.modules.businessUnitCode.repositories.BusinessUnitCodeRepository;
import com.gmf.user_management.modules.unit.dto.UnitDTO;
import com.gmf.user_management.modules.unit.dto.UnitPredicate;
import com.gmf.user_management.modules.unit.dto.UnitRequestDto;
import com.gmf.user_management.modules.unit.dto.UnitResponDto;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
import com.gmf.user_management.modules.unit.repository.UnitRepository;
import com.gmf.user_management.modules.unitJobCode.repository.UnitJobCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final BusinessUnitCodeRepository businessUnitCodeRepository;
    private final UnitJobCodeRepository unitJobCodeRepository;

    private UnitResponDto unitRespon(UnitEntity unitEntity)throws JsonProcessingException {
        return UnitResponDto.builder()
                .idUnit(unitEntity.getIdUnit())
                .businessUnitCodeList(unitEntity.getBusinessUnitCodeList())
                .unit(unitEntity.getUnit())
                .createdAt(unitEntity.getCreatedAt())
                .createdBy(unitEntity.getCreatedBy())
                .updatedAt(unitEntity.getUpdatedAt())
                .updatedBy(unitEntity.getUpdatedBy())
                .build();
    }
    public UnitResponDto createUnit(UnitDTO request)throws Exception {
        try {
//            List<BusinessUnitCodeEntity> businessUnits = businessUnitCodeRepository.findAllById(request.getBusinessUnitCodeList());
//            if (unitRepository.existsByBusinessUnitCodeListIn(businessUnits)) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT,
//                        "Business Unit Code with id " + request.getBusinessUnitCodeList() + " is already used");
//            }
//            List<PICDeveloperEntity> picDeveloper = picDeveloperRepository.findByPersonalNameIsIn(request.getPicDeveloper());
//            if (picDeveloper.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
//            }
            UnitEntity unit = new UnitEntity();
            UnitEntity payload = unitPaylod(request, unit);
            unitRepository.save(payload);
            return unitRespon(payload);
        }catch (Exception e){throw new RuntimeException(e);}
    }

    public UnitResponDto updatedUnit(Long idUnit, UnitDTO request)throws Exception {
        UnitEntity data = unitRepository.findById(idUnit).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        UnitEntity payload = unitPaylod(request, data);
        unitRepository.saveAndFlush(payload);
        return unitRespon(payload);
    }

    public Boolean deleteUnit(Long idUnit) {
        unitRepository.deleteById(idUnit);
        return true;
    }

    public PaginationUtil<UnitEntity, UnitEntity> getUnitByBusinessUnitCodeId(Long businessUnitCodeId, Integer page, Integer size)  {
        Pageable paging = PageRequest.of(page -1 , size);
        BusinessUnitCodeEntity businessUnitCode = businessUnitCodeRepository.findById(businessUnitCodeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Unit Code not found"));
        Page<UnitEntity> units = unitRepository.findByBusinessUnitCodeListContains(businessUnitCode, paging);
        return new PaginationUtil<>(units, UnitEntity.class);
    }


    private UnitEntity unitPaylod(UnitDTO unitDTO, UnitEntity unitEntity) {
        List<BusinessUnitCodeEntity> allBusinessUnit = businessUnitCodeRepository.findByIdBusinessUnitCodeIsIn(unitDTO.getBusinessUnitCodeList());
        if (allBusinessUnit.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business code not found!");
        unitEntity.setBusinessUnitCodeList(allBusinessUnit);
        unitEntity.setUnit(unitDTO.getUnit());
        unitEntity.setCreatedBy(unitDTO.getCreatedBy());
        unitEntity.setUpdatedBy(unitDTO.getUpdatedBy());
        return unitEntity;
    }

    public PaginationUtil<UnitEntity, UnitResponDto> getAllUnit(Integer page, Integer size, UnitRequestDto requestDto) {
        Pageable paging = PageRequest.of(page -1 ,size, Sort.by(Sort.Order.asc("createdAt")));
        Specification<UnitEntity> specs = Specification.where(UnitPredicate.unit(requestDto.getUnit()));
        Page<UnitEntity> pages = unitRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, UnitResponDto.class);
    }


    public UnitResponDto getUnitById(Long idUnit) {
        UnitEntity unitEntity = JpaResultHelperUtil.getSingleResultFromOptional(unitRepository.findById(idUnit));
        if (unitEntity == null)throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + idUnit + " Not Found");
        return ObjectMapperUtil.map(unitEntity, UnitResponDto.class);
    }

    @Transactional
    public void deleteUnitsByIds(List<Long> unitIds) {
        List<UnitEntity> unitsToDelete = unitRepository.findAllById(unitIds);
        if (unitsToDelete.size() != unitIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Some Unit IDs were not found");
        }

        boolean hasRelation = unitJobCodeRepository
                .findAll()
                .stream()
                .anyMatch(unitJobCode ->
                        unitJobCode.getUnitList().stream()
                                .anyMatch(unit -> unitIds.contains(unit.getIdUnit()))
                );

        if (hasRelation) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete: One or more Units are used in UnitJobCode");
        }

        unitRepository.deleteAll(unitsToDelete);
    }

}
