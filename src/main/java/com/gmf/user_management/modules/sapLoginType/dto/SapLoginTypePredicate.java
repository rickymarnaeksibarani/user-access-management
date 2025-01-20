package com.gmf.user_management.modules.sapLoginType.dto;

import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
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
