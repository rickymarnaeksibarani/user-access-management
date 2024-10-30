package com.gmf.user_management.masterData.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFileDTO {
    String filename;
    String path;
    String mimeType;
    String size;
}
