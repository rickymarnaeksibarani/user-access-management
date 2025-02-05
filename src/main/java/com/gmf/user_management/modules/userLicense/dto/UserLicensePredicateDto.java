package com.gmf.user_management.modules.userLicense.dto;

import com.gmf.user_management.modules.applicationLicense.entities.ApplicationLicenseEntity;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.userLicense.entities.UserLicenseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserLicensePredicateDto {

    public static Specification<UserLicenseEntity> filterByPersonalName(String filterByName) {
        return (root, query, criteriaBuilder) -> {
            if (filterByName != null && !filterByName.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("personalName")),
                        "%" + filterByName.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UserLicenseEntity> filterByDinas(String filterByDinas) {
        return (root, query, criteriaBuilder) -> {
            if (filterByDinas != null && !filterByDinas.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("dinas")),
                        "%" + filterByDinas.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UserLicenseEntity> filterByPartner(String filterByPartner) {
        return (root, query, criteriaBuilder) -> {
            if (filterByPartner != null && !filterByPartner.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("partnerName")),
                        "%" + filterByPartner.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    //unit, personal number, passcard number
    public static Specification<UserLicenseEntity> filterByUnit(String filterByUnit) {
        return (root, query, criteriaBuilder) -> {
            if (filterByUnit != null && !filterByUnit.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("unit")),
                        "%" + filterByUnit.toLowerCase() + "%"
                );
            }
            return null;
        };
    }
    //passCardNumber
    public static Specification<UserLicenseEntity> filterByPassCardNumber(String filterByPassCardNumber) {
        return (root, query, criteriaBuilder) -> {
            if (filterByPassCardNumber != null && !filterByPassCardNumber.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("passCardNumber")),
                        "%" + filterByPassCardNumber.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UserLicenseEntity> filterByPersonalNumber(String filterByPersonalNumber) {
        return (root, query, criteriaBuilder) -> {
            if (filterByPersonalNumber != null && !filterByPersonalNumber.isEmpty()) {
                Join<UserLicenseEntity, PersonalEntity> personalJoin = root.join("personalList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(personalJoin.get("personalNumber")),
                        "%" + filterByPersonalNumber.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UserLicenseEntity> applicationId(Long applicationId) {
        return (root, query, criteriaBuilder) -> {
            if (applicationId != null) {
                Join<UserLicenseEntity, ApplicationLicenseEntity> appJoin = root.join("applicationLicenseList");
                return criteriaBuilder.equal(appJoin.get("idApplicationLicense"), applicationId);
            }
            return null;
        };
    }
}
