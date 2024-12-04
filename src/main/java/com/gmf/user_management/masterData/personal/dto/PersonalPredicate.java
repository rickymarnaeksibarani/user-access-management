package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PersonalPredicate {
    public static Specification<PersonalEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("personalName")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity> activeStatus(List<Status> activeStatus) {
        return (root, query, criteriaBuilder) -> {
            if (activeStatus != null && !activeStatus.isEmpty()) {
                return root.get("activeStatus").in(activeStatus);
            }
            return null;
        };
    }
}
