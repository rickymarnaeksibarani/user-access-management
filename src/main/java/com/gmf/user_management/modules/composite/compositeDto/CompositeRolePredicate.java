package com.gmf.user_management.modules.composite.compositeDto;

import com.gmf.user_management.modules.composite.compositeEntities.CompositeRoleEntity;
import org.springframework.data.jpa.domain.Specification;

public class CompositeRolePredicate {
    public static Specification<CompositeRoleEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("compositeRole")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
}
