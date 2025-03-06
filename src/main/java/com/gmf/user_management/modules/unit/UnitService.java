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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final BusinessUnitCodeRepository businessUnitCodeRepository;

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
//        boolean exists = unitRepository.existsByBusinessUnitCodeListIn(allBusinessUnit);
//        if (exists){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Unit " + unitDTO.getUnit() + "is already used in "+ unitDTO.getBusinessUnitCodeList());
//        }

        unitEntity.setBusinessUnitCodeList(allBusinessUnit);
        unitEntity.setUnit(unitDTO.getUnit());
        unitEntity.setCreatedBy(unitDTO.getCreatedBy());
        unitEntity.setUpdatedBy(unitDTO.getUpdatedBy());
        return unitEntity;
    }

    public PaginationUtil<UnitEntity, UnitResponDto> getAllUnit(Integer page, Integer size, UnitRequestDto requestDto) {
        Pageable paging = PageRequest.of(page -1 ,size);
        Specification<UnitEntity> specs = Specification.where(UnitPredicate.unit(requestDto.getUnit()));
        Page<UnitEntity> pages = unitRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, UnitResponDto.class);
    }


    public UnitResponDto getUnitById(Long idUnit) {
        UnitEntity unitEntity = JpaResultHelperUtil.getSingleResultFromOptional(unitRepository.findById(idUnit));
        if (unitEntity == null)throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + idUnit + " Not Found");
        return ObjectMapperUtil.map(unitEntity, UnitResponDto.class);
    }
}
