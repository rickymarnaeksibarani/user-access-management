package com.gmf.user_management.modules.sapLoginType.repository;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SapLoginTypeRepository extends JpaRepository<SapLoginTypeEntity, Long>, JpaSpecificationExecutor<SapLoginTypeEntity> {
    List<SapLoginTypeEntity> findByIdSapLoginTypeIsIn(List<Long> id);
}
