package com.gmf.user_management.modules.businessUnitCode.repositories;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessUnitCodeRepository extends JpaRepository<BusinessUnitCodeEntity, Long>, JpaSpecificationExecutor<BusinessUnitCodeEntity> {
    List<BusinessUnitCodeEntity> findByIdBusinessUnitCodeIsIn(List<Long> id);

    Page<BusinessUnitCodeEntity> findByDinas(String dinas, Pageable pageable);
}
