package com.gmf.user_management.masterData.composite;

import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositeRoleRepository extends JpaRepository<CompositeRoleEntity, Long>, JpaSpecificationExecutor<CompositeRoleEntity> {
    Long countByJobCodeEntityList(Long jobCodeEntityList);
}
