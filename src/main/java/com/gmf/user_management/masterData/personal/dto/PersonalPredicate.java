package com.gmf.user_management.masterData.personal.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
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

    //Get Personal by partnerId
    public static Specification<PersonalEntity> dinas(String filterByDinas) {
        return (root, query, criteriaBuilder) -> {
            if (filterByDinas != null && !filterByDinas.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("dinas")),
                        "%" + filterByDinas.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity> unit(String filterByUnit) {
        return (root, query, criteriaBuilder) -> {
            if (filterByUnit != null && !filterByUnit.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("unit")),
                        "%" + filterByUnit.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity> isPic(Boolean isPic) {
        return (root, query, criteriaBuilder) -> {
            if (isPic == null) {
                return criteriaBuilder.conjunction(); // No filter applied for isPic
            }
            return isPic ? criteriaBuilder.isTrue(root.get("isPic")) : criteriaBuilder.isFalse(root.get("isPic"));
        };
    }

    public static Specification<PersonalEntity> startDate(Date startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
        };
    }

    public static Specification<PersonalEntity> expiredDate(Date expiredDate) {
        return (root, query, criteriaBuilder) -> {
            if (expiredDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("expiredDate"), expiredDate);
        };
    }

    public static Specification<PersonalEntity> filterByPartnerId(Long partnerExternal) {
        return (root, query, criteriaBuilder) -> {
            if (partnerExternal == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("partnerExternal"), partnerExternal);
        };
    }

}
