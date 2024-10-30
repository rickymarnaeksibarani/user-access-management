package com.gmf.user_management.masterData.partner;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.partner.entities.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long>, JpaSpecificationExecutor<PartnerEntity> {
    List<PartnerEntity> findByIdPartnerIsIn(List<Long> id);

}
