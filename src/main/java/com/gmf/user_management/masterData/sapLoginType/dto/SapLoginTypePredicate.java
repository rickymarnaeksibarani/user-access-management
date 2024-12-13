package com.gmf.user_management.masterData.sapLoginType.dto;

import com.gmf.user_management.masterData.sapLoginType.entities.SapLoginTypeEntity;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.data.jpa.domain.Specification;

public class SapLoginTypePredicate {
    public static Specification<SapLoginTypeEntity> filterSapLoginType(String filterSapLoginType) {
        return (root, query, criteriaBuilder) -> {
            if (filterSapLoginType != null && !filterSapLoginType.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("loginType")),
                        "%" + filterSapLoginType.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
