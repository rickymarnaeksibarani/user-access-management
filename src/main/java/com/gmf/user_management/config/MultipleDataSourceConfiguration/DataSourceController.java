package com.gmf.user_management.config.MultipleDataSourceConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/get")
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping("/relationExternal")
    public List<Map<String, Object>>getExternalDataRelation(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size
    ){
        return dataSourceService.getExternalDataRelation(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getContractById(@PathVariable("id") Long contractId) {
        Map<String, Object> contract = dataSourceService.getContractById(contractId);
        if (contract != null) {
            return ResponseEntity.ok(contract);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
