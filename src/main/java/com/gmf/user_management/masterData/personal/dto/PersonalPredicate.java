package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.data.jpa.domain.Specification;

public class PersonalPredicate {
    public static Specification<UnitEntity> searchTerm(String searchTerm) {
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
}
