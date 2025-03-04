package com.gmf.user_management.config.multipleDataSourceConfiguration.service;

import com.gmf.user_management.config.multipleDataSourceConfiguration.dto.DataSourceDTO;
import com.gmf.user_management.config.multipleDataSourceConfiguration.repository.ExternalRepository;
import com.gmf.user_management.core.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataSourceService {

    private final ExternalRepository externalRepository;

    public PaginationUtil<Map<String, Object>, Map<String, Object>> getExternalDataRelation(
            DataSourceDTO dataSourceDTO) {

        Page<Map<String, Object>> externalDataPage = externalRepository.findContractsWithPartners(
                dataSourceDTO, PageRequest.of(dataSourceDTO.getPage()-1 , dataSourceDTO.getSize()));
        if (externalDataPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No external data found for the given criteria.");
        }
        return new PaginationUtil<>(externalDataPage.getContent(), dataSourceDTO.getPage(), externalDataPage.getTotalElements(),
                externalDataPage.getTotalPages(), dataSourceDTO.getSize(), externalDataPage.hasPrevious(), externalDataPage.hasNext());
    }
//    Pageable paging = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("createdAt"))); Sort.by(Sort.Order.asc("start"))


    public Map<String, Object> getContractById(Long contractId) {
        return externalRepository.findContractById(contractId);
    }

    public Map<String, Object> getPartnerByContractId(Long contractId) {
        return externalRepository.findPartnerByContractId(contractId);
    }

    public List<Map<String, Object>> getByPartnerId(Long partnerId) {
        List<Map<String, Object>> contracts = externalRepository.findByPartnerId(partnerId);

        if (contracts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contracts found for the given partner ID.");
        }

        return contracts;
    }

}
