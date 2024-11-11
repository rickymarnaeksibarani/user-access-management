package com.gmf.user_management.masterData.applicationLicense.dto;

import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.sapLoginType.entities.SapLoginTypeEntity;
import org.springframework.data.jpa.domain.Specification;

public class ApplicationLicensePredicate {
    public static Specification<ApplicationLicenseEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("applicationName")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
