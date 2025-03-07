package com.gmf.user_management.modules.jobCode.dto;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class JobCodePredicate {
    public static Specification<JobCodeEntity> searchJobCode(String searchJobCode) {
        return (root, query, criteriaBuilder) -> {
            if (searchJobCode != null && !searchJobCode.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("jobCode")),
                        "%" + searchJobCode.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<JobCodeEntity> filterByJobPosition(String filterByJobPosition) {
        return (root, query, criteriaBuilder) -> {
            if (filterByJobPosition != null && !filterByJobPosition.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("jobPosition")),
                        "%" + filterByJobPosition.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

}
