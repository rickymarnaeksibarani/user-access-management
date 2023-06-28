package com.gmf.user_management.domains.vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorMainRepository extends JpaRepository<VendorEntity, Long>, JpaSpecificationExecutor<VendorEntity>, VendorRepository {
}
