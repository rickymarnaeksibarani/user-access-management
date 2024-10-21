package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.domain.Specification;

public class BusinessUnitCodePredicate {
    public static Specification<BusinessUnitCodeEntity> searchTerm(String searchTerm) {

        if (searchTerm == null || searchTerm.isEmpty())
            return null;

        searchTerm = "%" + searchTerm + "%";

        System.out.println(searchTerm);

        String finalSearchTerm = searchTerm;
        return (root, query, builder) ->
                builder.or(
                        builder.like(builder.upper(root.get("jobPosition")), finalSearchTerm.toUpperCase()),
                        builder.like(builder.upper(root.get("job_code")), finalSearchTerm.toUpperCase()),
                        builder.like(builder.upper(root.get("createdBy")), finalSearchTerm.toUpperCase()),
                        builder.like(builder.upper(root.get("updatedBy")), finalSearchTerm.toUpperCase())
                );
    }
}
