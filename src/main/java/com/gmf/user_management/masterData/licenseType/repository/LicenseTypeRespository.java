package com.gmf.user_management.masterData.licenseType.repository;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseTypeRespository extends JpaRepository<LicenseTypeEntity, Long>, JpaSpecificationExecutor<LicenseTypeEntity> {
    List<LicenseTypeEntity> findByIdLicenseTypeIsIn(List<Long> id);

    @Query("SELECT l.licenseName, COUNT(l) FROM LicenseTypeEntity l GROUP BY l.licenseName")
    List<Object[]> countByLicenseName();

//    Long countLicenseName(String licenseName);

//    List<Object[]> countLicenseName();

//    @Query(value = "SELECT COUNT (*)  license_name FROM tb_license_type")
//    Long countLicenseTypeEntityBy
}
