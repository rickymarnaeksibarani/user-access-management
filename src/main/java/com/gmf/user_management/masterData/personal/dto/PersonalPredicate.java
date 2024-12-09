package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PersonalPredicate {
    public static Specification<PersonalEntity> filterByName(String filterByName) {
        return (root, query, criteriaBuilder) -> {
            if (filterByName != null && !filterByName.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("personalName")),
                        "%" + filterByName.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity>searchByName(String searchByName){
        return (root, query, builder) -> {
            if (searchByName != null && !searchByName.isEmpty()){
                return builder.like(
                        builder.lower(root.get("personalName")),
                        "%" + searchByName.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity> filterByStatus(List<Status> filterByStatus) {
        return (root, query, criteriaBuilder) -> {
            if (filterByStatus != null && !filterByStatus.isEmpty()) {
                return root.get("activeStatus").in(filterByStatus);
            }
            return null;
        };
    }


}
