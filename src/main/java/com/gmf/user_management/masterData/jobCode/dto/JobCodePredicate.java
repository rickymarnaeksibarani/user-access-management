package com.gmf.user_management.masterData.jobCode.dto;

import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class JobCodePredicate {
//    public static Specification<JobCodeEntity> searchTerm(String searchTerm) {
//
//        if (searchTerm == null || searchTerm.isEmpty())
//            return null;
//
//        searchTerm = "%" + searchTerm + "%";
//
//        System.out.println(searchTerm);
//
//        String finalSearchTerm = searchTerm;
//        return (root, query, builder) ->
//                builder.or(
//                        builder.like(builder.upper(root.get("jobPosition")), finalSearchTerm.toUpperCase()),
//                        builder.like(builder.upper(root.get("jobCode")), finalSearchTerm.toUpperCase()),
//                        builder.like(builder.upper(root.get("createdBy")), finalSearchTerm.toUpperCase()),
//                        builder.like(builder.upper(root.get("updatedBy")), finalSearchTerm.toUpperCase())
//                );
//    }

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

}
