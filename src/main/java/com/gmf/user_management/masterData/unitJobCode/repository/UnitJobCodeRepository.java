package com.gmf.user_management.masterData.unitJobCode.repository;

import com.gmf.user_management.masterData.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitJobCodeRepository extends JpaRepository<UnitJobCodeEntity, Long>, JpaSpecificationExecutor<UnitJobCodeEntity> {
}
