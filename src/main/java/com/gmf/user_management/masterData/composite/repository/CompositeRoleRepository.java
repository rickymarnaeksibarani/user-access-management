package com.gmf.user_management.masterData.composite.repository;

import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositeRoleRepository extends JpaRepository<CompositeRoleEntity, Long>, JpaSpecificationExecutor<CompositeRoleEntity> {
    int countByJobCodeEntityList_idJobCode(Long jobCodeId);

    List<CompositeRoleEntity> findByJobCodeEntityList_idJobCode(Long jobCodeId);
}
