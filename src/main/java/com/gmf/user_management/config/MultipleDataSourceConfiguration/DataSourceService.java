package com.gmf.user_management.config.MultipleDataSourceConfiguration;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.repository.ExternalRepository;
import com.gmf.user_management.core.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DataSourceService {
    @Autowired
    private ExternalRepository externalRepository;

    @Autowired
    public DataSourceService(ExternalRepository externalRepository){
        this.externalRepository=externalRepository;
    }

    /*
    TODO: 03/12/2024 > sub tasks:
    1. filterByStatus
    2. filterByStartDate
    3. filterByExpiredDate
     */

    public PaginationUtil<Map<String, Object>, Map<String, Object>> getExternalDataRelation(
            int page, int size, String filterByStatus, LocalDate filterByStart, LocalDate filterByEnd) {
        int offset = (page - 1) * size;
        List<Map<String, Object>> data = externalRepository.findContractsWithPartners(size, offset, filterByStatus, filterByStart, filterByEnd);
        long totalItems = externalRepository.countContracts(filterByStatus, filterByStart, filterByEnd);
        int lastPage = (int) Math.ceil((double) totalItems / size);

        return new PaginationUtil<>(
                data,
                page,
                totalItems,
                lastPage,
                size,
                page > 1,
                page < lastPage
        );
    }

    public Map<String, Object> getContractById(Long contractId) {
        return externalRepository.findContractById(contractId);
    }

}
