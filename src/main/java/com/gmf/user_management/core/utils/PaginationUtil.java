package com.gmf.user_management.core.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginationUtil<InClass, OutClass> {
    private List<OutClass> data;
    private Integer currentPage;
    private long totalItems;
    private Integer lastPage;
    private Integer totalItemsPerPage;
    private Boolean hasPrev;
    private Boolean hasNext;

    public PaginationUtil(Page<InClass> pagedResult, Class<OutClass> outCLass) {
        this.data = ObjectMapperUtil.mapAll(pagedResult.toList(), outCLass);
        this.currentPage = pagedResult.getNumber() + 1;
        this.totalItems = pagedResult.getTotalElements();
        this.lastPage = pagedResult.getTotalPages();
        this.totalItemsPerPage = pagedResult.getNumberOfElements();
        this.hasNext = pagedResult.hasNext();
        this.hasPrev = pagedResult.hasPrevious();
    }
}
