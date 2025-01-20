package com.gmf.user_management.modules.unitJobCode.dto;

import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class UnitJobCodePredicate {
    public static Specification<UnitJobCodeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("businessUnitCodeId")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
