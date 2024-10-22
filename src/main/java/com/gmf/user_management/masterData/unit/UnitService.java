package com.gmf.user_management.masterData.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.businessUnitCode.repositories.BusinessUnitCodeRepository;
import com.gmf.user_management.masterData.unit.dto.UnitDTO;
import com.gmf.user_management.masterData.unit.dto.UnitResponDto;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private BusinessUnitCodeRepository businessUnitCodeRepository;

    private UnitResponDto unitRespon(UnitEntity unitEntity)throws JsonProcessingException {
        return UnitResponDto.builder()
                .id_unit(unitEntity.getId_unit())
                .business_unit_code_id(unitEntity.getBusinessUnitCodeList())
                .unit(unitEntity.getUnit())
                .created_at(unitEntity.getCreated_at())
                .created_by(unitEntity.getCreated_by())
                .updated_at(unitEntity.getUpdated_at())
                .updated_by(unitEntity.getUpdated_by())
                .build();
    }
    public UnitResponDto createUnit(UnitDTO request)throws Exception {
        UnitEntity unit = new UnitEntity();
        UnitEntity payload = unitPaylod(request, unit);
        unitRepository.save(payload);
        return unitRespon(payload);
    }

    private UnitEntity unitPaylod(UnitDTO unitDTO, UnitEntity unitEntity) {
        List<BusinessUnitCodeEntity> allBusinessUnit = businessUnitCodeRepository.findByIdBusinessUnitCodeIsIn(unitDTO.getBusiness_unit_code_id());

        if (allBusinessUnit.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business code not found!");

        unitEntity.setBusinessUnitCodeList(allBusinessUnit);
        unitEntity.setUnit(unitDTO.getUnit());
//        unitEntity.setCreated_at(unitDTO.getCreated_at());
        unitEntity.setCreated_by(unitDTO.getCreated_by());
//        unitEntity.setUpdated_at(unitDTO.getUpdated_at());
        unitEntity.setUpdated_by(unitDTO.getUpdated_by());
        return unitEntity;
    }
}
