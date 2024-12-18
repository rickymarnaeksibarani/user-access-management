package com.gmf.user_management.masterData.composite.repository;

import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositeRoleRepository extends JpaRepository<CompositeRoleEntity, Long>, JpaSpecificationExecutor<CompositeRoleEntity> {
    long countByJobCodeList_idJobCode(Long jobCodeId);

    Page<CompositeRoleEntity> findByJobCodeList_idJobCode(Long jobCodeId, Pageable pageable);
}
