package com.gmf.user_management.domains.vendor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class VendorPredicate {
    public static Specification<VendorEntity> withSearchTerm(@Nullable String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty())
            return null;

        return (root, query, builder) ->
                builder.or(
                        builder.like(builder.upper(root.get("vendorName")), "%" + searchTerm.toUpperCase() + "%")
                );
    }
}
