package com.gmf.user_management.modules.applicationLicense.dto;

import com.gmf.user_management.core.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationLicenseRequest {
    private String searchTerm; //application name
    private List<Status> activeStatus; //status
    private Integer page;
    private Integer size;

    ApplicationLicenseRequest(){
        {
            if(this.getPage() == null) {
                this.page = 1;
            }
            if(this.getSize() == null) {
                this.size = 10;
            }
        }
    }
}
