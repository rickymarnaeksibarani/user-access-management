package com.gmf.user_management.modules.sapLoginType.repository;

import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SapLoginTypeRepository extends JpaRepository<SapLoginTypeEntity, Long>, JpaSpecificationExecutor<SapLoginTypeEntity> {
}
