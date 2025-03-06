package com.gmf.user_management.config.partner.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {
    private Integer page;
    private Integer size;
    private String searchTerm;
    private String filterByStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate filterByStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate filterByEnd;
}
