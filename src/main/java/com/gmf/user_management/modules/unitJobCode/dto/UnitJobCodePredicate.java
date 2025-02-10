package com.gmf.user_management.modules.unitJobCode.dto;

import com.gmf.user_management.modules.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.unit.entities.UnitEntity;
import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class UnitJobCodePredicate {
    public static Specification<UnitJobCodeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("businessUnitCodeId")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UnitJobCodeEntity> filterByUnit(String filterByUnit) {
        return (root, query, criteriaBuilder) -> {
            if (filterByUnit != null && !filterByUnit.isEmpty()) {
                Join<UnitJobCodeEntity, UnitEntity> unitJoin = root.join("unitList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(unitJoin.get("unit")),
                        "%" + filterByUnit.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UnitJobCodeEntity> filterByJobCode(String filterByJobCode) {
        return (root, query, criteriaBuilder) -> {
            if (filterByJobCode != null && !filterByJobCode.isEmpty()) {
                Join<UnitJobCodeEntity, JobCodeEntity> jobCodeJoin = root.join("jobCodeList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(jobCodeJoin.get("jobCode")),
                        "%" + filterByJobCode.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<UnitJobCodeEntity> filterByDinas(String filterByDinas){
        return (root, query, criteriaBuilder) -> {
            if (filterByDinas != null && !filterByDinas.isEmpty()){
                Join<UnitJobCodeEntity, UnitEntity> unitJoin = root.join("unitList");
                Join<UnitEntity, BusinessUnitCodeEntity>businessUnitJoin = unitJoin.join("businessUnitCodeList");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(businessUnitJoin.get("dinas")),
                        "%" + filterByDinas.toLowerCase()+ "%"
                );
            }
            return null;
        };
    }

}
