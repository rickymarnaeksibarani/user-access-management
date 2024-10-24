package com.gmf.user_management.masterData.partner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmf.user_management.core.enums.Status;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class PartnerDto {
    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("keyNumber")
    private String keyNumber;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("expiredDate")
    private Date expiredDate;

    @Enumerated(EnumType.STRING)
    @JsonProperty("activeStatus")
    private Status activeStatus;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;
}
