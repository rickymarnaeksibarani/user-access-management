package com.gmf.user_management.config.MultipleDataSourceConfiguration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/get")
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping("/contractsExternal")
    public List<Map<String, Object>> getExternalDataContracts(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size
    ) {
        return dataSourceService.getExternalDataContracts(page, size);
    }

    @GetMapping("/partnersExternal")
    public List<Map<String, Object>>getExternalDataPartners(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size
    ){
        return dataSourceService.getExternalDataPartners(page, size);
    }
    @GetMapping("/relationExternal")
    public List<Map<String, Object>>getExternalDataRelation(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size
    ){
        return dataSourceService.getExternalDataRelation(page, size);
    }

//    @GetMapping("/findContractsWithPartners")
}
