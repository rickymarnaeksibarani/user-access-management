package com.gmf.user_management.config.multipleDataSourceConfiguration.controller;

import com.gmf.user_management.config.multipleDataSourceConfiguration.dto.DataSourceDTO;
import com.gmf.user_management.config.multipleDataSourceConfiguration.service.DataSourceService;
import com.gmf.user_management.core.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/get")
@RequiredArgsConstructor
public class DataSourceController {

    private final DataSourceService dataSourceService;

    @GetMapping(value = "/relationExternal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginationUtil<Map<String, Object>, Map<String, Object>>> getExternalDataRelation(
            DataSourceDTO dataSourceDTO
            ) {
        if (dataSourceDTO.getPage() < 1) {
            return ResponseEntity.badRequest().body(null);
        }
        PaginationUtil<Map<String, Object>, Map<String, Object>> response = dataSourceService.getExternalDataRelation(dataSourceDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getContractById(@PathVariable("id") Long contractId) {
        Map<String, Object> contract = dataSourceService.getContractById(contractId);
        if (contract != null) {
            return ResponseEntity.ok(contract);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/contract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPartnerByContractId(@PathVariable("contractId") Long contractId) {
        Map<String, Object> contract = dataSourceService.getPartnerByContractId(contractId);
        if (contract != null) {
            return ResponseEntity.ok(contract);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/partner/{partnerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getContractsByPartnerId(@PathVariable("partnerId") Long partnerId) {
        List<Map<String, Object>> contracts = dataSourceService.getByPartnerId(partnerId);
        if (contracts != null && !contracts.isEmpty()) {
            return ResponseEntity.ok(contracts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
