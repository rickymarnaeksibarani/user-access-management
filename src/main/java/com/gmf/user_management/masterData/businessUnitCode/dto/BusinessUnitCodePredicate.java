package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class BusinessUnitCodePredicate {
    public static Specification<BusinessUnitCodeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Assuming the field you want to search by is 'unitCode'
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("dinas")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
