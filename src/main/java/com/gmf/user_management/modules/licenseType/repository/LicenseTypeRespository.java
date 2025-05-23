package com.gmf.user_management.modules.licenseType.repository;

import com.gmf.user_management.modules.licenseType.entities.LicenseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseTypeRespository extends JpaRepository<LicenseTypeEntity, Long>, JpaSpecificationExecutor<LicenseTypeEntity> {
    List<LicenseTypeEntity> findByIdLicenseTypeIsIn(List<Long> id);

    @Query("SELECT l.licenseName, COUNT(l) FROM LicenseTypeEntity l GROUP BY l.licenseName")
    List<Object[]> countByLicenseName();

    @Query("SELECT l.licenseName, COUNT(DISTINCT p.idPersonal)" +
            "FROM PersonalEntity p JOIN p.licenseTypeList l GROUP BY l.licenseName")
    List<Object[]> countPersonalByLicense();

    boolean existsByLicenseName(@NotEmpty String licenseName);

    Optional<LicenseTypeEntity> findByLicenseName(@NotEmpty String licenseName);
}
