package com.gmf.user_management.masterData.businessUnitCode.dto;

import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BusinessUnitCodePredicate {
    public static Specification<BusinessUnitCodeEntity> searchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("businessUnitCode")),
                        "%" + searchTerm.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

//    public static Specification<BusinessUnitCodeEntity> dinas(String dinas) {
//        return (root, query, criteriaBuilder) -> {
//            if (dinas != null && !dinas.isEmpty()) {
//                return criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get("dinas")),
//                        "%" + dinas.toLowerCase() + "%"
//                );
//            }
//            return null;
//        };
//    }

    public static Specification<BusinessUnitCodeEntity> searchNamePartner(List<String> partnerName) {
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

    public static Specification<BusinessUnitCodeEntity> dinas(List<String> dinas){
        return (root, query, builder)->{
            if (dinas != null && !dinas.isEmpty()){
                List<Predicate> predicates = new ArrayList<>();
                for (String name: dinas){
                    predicates.add(
                            builder.like(
                                    builder.lower(root.get("dinas")),
                                    "%" + name.toLowerCase()+"%"
                            )
                    );
                }
                return builder.or(predicates.toArray(new Predicate[0]));
            }
            return builder.conjunction();
        };
    }
}
