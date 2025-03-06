package com.gmf.user_management.config.partner.service;

import com.gmf.user_management.config.partner.dto.PartnerDTO;
import com.gmf.user_management.config.partner.repository.PartnerRepository;
import com.gmf.user_management.core.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PaginationUtil<Map<String, Object>, Map<String, Object>> getExternalDataRelation(
            PartnerDTO partnerDTO) {

        Page<Map<String, Object>> externalDataPage = partnerRepository.findContractsWithPartners(
                partnerDTO, PageRequest.of(partnerDTO.getPage()-1 , partnerDTO.getSize()));
        if (externalDataPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No external data found for the given criteria.");
        }
        return new PaginationUtil<>(externalDataPage.getContent(), partnerDTO.getPage(), externalDataPage.getTotalElements(),
                externalDataPage.getTotalPages(), partnerDTO.getSize(), externalDataPage.hasPrevious(), externalDataPage.hasNext());
    }


    public Map<String, Object> getContractById(Long contractId) {
        return partnerRepository.findContractById(contractId);
    }

    public Map<String, Object> getPartnerByContractId(Long contractId) {
        return partnerRepository.findPartnerByContractId(contractId);
    }

    public List<Map<String, Object>> getByPartnerId(Long partnerId) {
        List<Map<String, Object>> contracts = partnerRepository.findByPartnerId(partnerId);
        if (contracts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contracts found for the given partner ID.");
        }
        return contracts;
    }

}
