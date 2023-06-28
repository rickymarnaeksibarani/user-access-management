package com.gmf.user_management.domains.vendor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {

    @JsonProperty("id")
    private Long idUserSource;

    @JsonProperty("name")
    private String companyName;

    @JsonProperty("address")
    private String companyAddress;

    @JsonProperty("picName")
    private String picName;

    @JsonProperty("picContactNumber")
    private String picContactNumber;

    @JsonProperty("vendorEmail")
    private String email;

    @JsonProperty("status")
    private String activeStatus;

    @JsonProperty("updatedBy")
    private String changedBy;

    @JsonProperty("createdAt")
    private LocalDateTime created_at;

    @JsonProperty("updatedAt")
    private LocalDateTime updated_at;
}
