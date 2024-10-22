package com.gmf.user_management.masterData.partner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.Status;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PartnerDto {
    @JsonProperty("id_partner")
    private Long id_partner;

    @JsonProperty("company_name")
    private String company_name;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("key_number")
    private String key_number;

    @JsonProperty("start_date")
    private Date start_date;

    @JsonProperty("expired_date")
    private Date expired_date;

    @Enumerated(EnumType.STRING)
    @JsonProperty("active_status")
    private Status active_status;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("created_by")
    private String created_by;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

    @JsonProperty("updated_by")
    private String updated_by;
}
