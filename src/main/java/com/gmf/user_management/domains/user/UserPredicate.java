package com.gmf.user_management.domains.user;

import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class UserPredicate {
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
