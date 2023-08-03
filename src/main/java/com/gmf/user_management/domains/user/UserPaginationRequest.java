package com.gmf.user_management.domains.user;

import com.gmf.user_management.core.validations.Sanitizer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPaginationRequest {

    @Sanitizer
    private String email;

    @Sanitizer
    private String userSourceId;

    @Sanitizer
    private String searchTerm;

}
