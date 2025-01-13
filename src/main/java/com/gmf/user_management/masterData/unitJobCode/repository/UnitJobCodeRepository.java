package com.gmf.user_management.masterData.unitJobCode.repository;

import com.gmf.user_management.masterData.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitJobCodeRepository extends JpaRepository<UnitJobCodeEntity, Long>, JpaSpecificationExecutor<UnitJobCodeEntity> {
    Page<UnitJobCodeEntity> findByJobCodeList_IdJobCode(Long jobCodeId, Pageable pageable);
    Page<UnitJobCodeEntity> findByUnitList_IdUnit(Long unitId, Pageable pageable);
    List<UnitJobCodeEntity> findByUnitList_IdUnit(Long unitId);


}
