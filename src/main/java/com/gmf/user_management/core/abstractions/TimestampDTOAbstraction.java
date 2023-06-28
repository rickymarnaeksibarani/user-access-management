package com.gmf.user_management.core.abstractions;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TimestampDTOAbstraction {

    @JsonProperty("createdAt")
    private LocalDateTime created_at;

    @JsonProperty("updatedAt")
    private LocalDateTime updated_at;
}
