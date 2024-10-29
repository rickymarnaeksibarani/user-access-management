package com.gmf.user_management.masterData.licenseType.dto;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import org.springframework.data.jpa.domain.Specification;

public class LicenseTypePredicate {
    public static Specification<LicenseTypeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("licenseName")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
