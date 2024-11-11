package com.gmf.user_management.masterData.sapLoginType.dto;

import com.gmf.user_management.masterData.sapLoginType.entities.SapLoginTypeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.data.jpa.domain.Specification;

public class SapLoginTypePredicate {
    public static Specification<SapLoginTypeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("loginType")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
