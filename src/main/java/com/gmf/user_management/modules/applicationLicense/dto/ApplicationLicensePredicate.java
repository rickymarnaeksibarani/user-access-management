package com.gmf.user_management.modules.applicationLicense.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    public static Specification<ApplicationLicenseEntity> activeStatus(List<Status> activeStatus) {
        return (root, query, criteriaBuilder) -> {
            if (activeStatus != null && !activeStatus.isEmpty()) {
                return root.get("activeStatus").in(activeStatus);
            }
            return null;
        };
    }
}
