package com.gmf.user_management.domains.user;

import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class UserPredicate {
    public static Specification<UserActiveEntity> equalSourceId(@Nullable String email) {
        if (email == null || email.isEmpty())
            return null;

        Long sourceId = Long.parseLong(email);

        return (root, query, builder) ->
            builder.or(
                    builder.equal(root.get("sourceId"), sourceId)
            );
    }


    public static Specification<UserActiveEntity> searchTerm(String searchTerm) {

        if (searchTerm == null || searchTerm.isEmpty())
            return null;

        searchTerm = "%" + searchTerm + "%";

        System.out.println(searchTerm);

        String finalSearchTerm = searchTerm;
        return (root, query, builder) ->
            builder.or(
                    builder.like(root.get("username"), finalSearchTerm),
                    builder.like(root.get("workstation"), finalSearchTerm),
                    builder.like(root.get("identityNumber"), finalSearchTerm),
                    builder.like(root.get("firstName"), finalSearchTerm),
                    builder.like(root.get("lastName"), finalSearchTerm),
                    builder.like(root.get("email"), finalSearchTerm),
                    builder.like(root.get("passCardNumber"), finalSearchTerm)
            );
    }


    public static Specification<UserActiveEntity> likeEmail(@Nullable String email) {
        if (email == null || email.isEmpty())
            return null;

        return (root, query, builder) ->
                builder.or(
                        builder.like(builder.upper(root.get("email")), "%" + email.toUpperCase() + "%")
                );
    }
    public static Specification<UserActiveEntity> equalUsername(String username) {

        return (root, query, builder) ->
                builder.and(
                        builder.equal(root.get("username"), username)
                );
    }
}
