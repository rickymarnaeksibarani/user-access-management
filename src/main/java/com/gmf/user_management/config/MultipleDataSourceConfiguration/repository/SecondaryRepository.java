package com.gmf.user_management.config.MultipleDataSourceConfiguration.repository;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.entities.SecondaryPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryRepository extends JpaRepository<SecondaryPartnerEntity, Long>, JpaSpecificationExecutor<SecondaryPartnerEntity> {
}
