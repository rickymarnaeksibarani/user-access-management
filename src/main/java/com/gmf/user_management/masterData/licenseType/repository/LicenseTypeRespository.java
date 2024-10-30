package com.gmf.user_management.masterData.licenseType.repository;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseTypeRespository extends JpaRepository<LicenseTypeEntity, Long>, JpaSpecificationExecutor<LicenseTypeEntity> {
    List<LicenseTypeEntity> findByIdLicenseTypeIsIn(List<Long> id);

}
