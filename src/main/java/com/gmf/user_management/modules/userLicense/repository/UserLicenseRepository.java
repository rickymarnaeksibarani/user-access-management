package com.gmf.user_management.modules.userLicense.repository;

import com.gmf.user_management.modules.userLicense.entities.UserLicenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLicenseRepository extends JpaRepository<UserLicenseEntity, Long>, JpaSpecificationExecutor<UserLicenseEntity> {
    Page<UserLicenseEntity> findByApplicationLicenseList_IdApplicationLicense(Long applicationLicenseId, Pageable pageable);

    Page<UserLicenseEntity> findByPersonalList_IdPersonal(Long idUserLicense, Pageable pageable);
}
