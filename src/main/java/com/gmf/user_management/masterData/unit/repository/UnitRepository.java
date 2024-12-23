package com.gmf.user_management.masterData.unit.repository;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long>, JpaSpecificationExecutor<UnitEntity> {

    List<UnitEntity> findByIdUnitIsIn(Collection<Long> id);

    Page<UnitEntity> findByBusinessUnitCodeListContains(BusinessUnitCodeEntity businessUnitCode, Pageable pageable);
}
