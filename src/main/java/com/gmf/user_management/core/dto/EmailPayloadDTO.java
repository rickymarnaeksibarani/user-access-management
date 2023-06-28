package com.gmf.user_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailPayloadDTO {
    private String appOrigin;
    private String from;
    private String subject;
    private String body;
    private String receiver;
    private List<String> cc;
}
