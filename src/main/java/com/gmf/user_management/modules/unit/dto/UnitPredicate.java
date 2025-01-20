package com.gmf.user_management.modules.unit.dto;

import com.gmf.user_management.modules.unit.entities.UnitEntity;
import org.springframework.data.jpa.domain.Specification;

public class UnitPredicate {
    public static Specification<UnitEntity> unit(String unit) {
        return (root, query, criteriaBuilder) -> {
            if (unit != null && !unit.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("unit")),
                        "%" + unit.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
