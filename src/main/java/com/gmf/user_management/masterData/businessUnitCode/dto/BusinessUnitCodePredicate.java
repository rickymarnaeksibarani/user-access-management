package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class BusinessUnitCodePredicate {
    public static Specification<BusinessUnitCodeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("businessUnitCode")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<BusinessUnitCodeEntity> dinas(String dinas) {
        return (root, query, criteriaBuilder) -> {
            if (dinas != null && !dinas.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("dinas")),
                        "%" + dinas.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
