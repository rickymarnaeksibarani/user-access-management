package com.gmf.user_management.modules.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gmf.user_management.core.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PersonalRequestDTO {
    private String filterByName;
    private List<Status> filterByStatus;
    private String searchByName;
    private String dinas;
    private String unit;
    private Boolean isPic;
    private List<String> partnerName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiredDate;
}
