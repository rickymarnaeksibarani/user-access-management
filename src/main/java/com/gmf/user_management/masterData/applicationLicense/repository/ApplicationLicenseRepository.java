package com.gmf.user_management.masterData.applicationLicense.repository;

import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationLicenseRepository extends JpaRepository<ApplicationLicenseEntity, Long>, JpaSpecificationExecutor<ApplicationLicenseEntity> {
}
