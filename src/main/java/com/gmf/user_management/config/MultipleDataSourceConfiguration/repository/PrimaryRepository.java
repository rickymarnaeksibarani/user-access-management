package com.gmf.user_management.config.MultipleDataSourceConfiguration.repository;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.entities.PrimaryPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryRepository extends JpaRepository<PrimaryPartnerEntity, Long>, JpaSpecificationExecutor<PrimaryPartnerEntity> {
}
