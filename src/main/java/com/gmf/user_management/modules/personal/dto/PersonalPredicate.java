package com.gmf.user_management.modules.personal.dto;

import com.gmf.user_management.core.enums.Status;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    public static Specification<PersonalEntity> uidIsNotNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("uid"));
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

    public static Specification<PersonalEntity>searchByKeyNumber(String searchByName){
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
                return criteriaBuilder.conjunction();
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

    public static Specification<PersonalEntity> searchNamePartner(List<String> partnerName) {
        return (root, query, builder) -> {
            if (partnerName != null && !partnerName.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (String name : partnerName) {
                    predicates.add(
                            builder.like(
                                    builder.lower(root.get("partnerName")),
                                    "%" + name.toLowerCase() + "%"
                            )
                    );
                }
                return builder.or(predicates.toArray(new Predicate[0]));
            }
            return builder.conjunction();
        };
    }

    public static Specification<PersonalEntity> personalNumber(String filterByPersonalNumber) {
        return (root, query, criteriaBuilder) -> {
            if (filterByPersonalNumber != null && !filterByPersonalNumber.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("personalNumber")),
                        "%" + filterByPersonalNumber.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<PersonalEntity> passCardNumber(String filterByPassCardNumber) {
        return (root, query, criteriaBuilder) -> {
            if (filterByPassCardNumber != null && !filterByPassCardNumber.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("passCardNumber")),
                        "%" + filterByPassCardNumber.toLowerCase() + "%"
                );
            }
            return null;
        };
    }


}
