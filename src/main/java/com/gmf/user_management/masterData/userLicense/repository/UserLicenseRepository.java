package com.gmf.user_management.masterData.userLicense.repository;

import com.gmf.user_management.masterData.userLicense.entities.UserLicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLicenseRepository extends JpaRepository<UserLicenseEntity, Long>, JpaSpecificationExecutor<UserLicenseEntity> {
}
