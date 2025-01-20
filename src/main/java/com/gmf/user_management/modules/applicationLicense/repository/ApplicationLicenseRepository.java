package com.gmf.user_management.modules.applicationLicense.repository;

import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationLicenseRepository extends JpaRepository<ApplicationLicenseEntity, Long>, JpaSpecificationExecutor<ApplicationLicenseEntity> {
    List<ApplicationLicenseEntity> findByIdApplicationLicenseIsIn(List<Long> applicationLicenseList);
}
