package com.gmf.user_management.masterData.unitJobCode.repository;

import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitJobCodeRepository extends JpaRepository<UnitJobCodeEntity, Long>, JpaSpecificationExecutor<UnitJobCodeEntity> {
    List<UnitJobCodeEntity> findByJobCodeList_IdJobCode(Long jobCodeId);
    List<UnitJobCodeEntity> findByUnitList_IdUnit(Long unitId);

}
