package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.domain.Specification;

public class PersonalPredicate {
    public static Specification<PersonalEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                String searchPattern = "%" + searchTerm.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("personalNumber")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("dinas")), searchPattern)
                );
            }
            return null;
        };
    }
}
