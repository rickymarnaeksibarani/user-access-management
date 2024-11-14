package com.gmf.user_management.config.MultipleDataSourceConfiguration;

import com.gmf.user_management.config.MultipleDataSourceConfiguration.repository.ExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Map<String, Object>> getExternalDataContracts(int page, int size){
        int offset = page*size;
        return externalRepository.findAllFromContractsTable(size, offset);
    }
    public List<Map<String, Object>> getExternalDataPartners(int page, int size){
        int offset = page*size;
        return externalRepository.findAllFromPartnersTable(size, offset);
    }

    public List<Map<String, Object>> getExternalDataRelation(int page, int size) {
        int offset = page*size;
        return externalRepository.findContractsWithPartners(size,offset);
    }
}
