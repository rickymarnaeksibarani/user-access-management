package com.gmf.user_management.masterData.licenseType;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.businessUnitCode.dto.BusinessUnitCodeResponDTO;
import com.gmf.user_management.masterData.businessUnitCode.entities.BusinessUnitCodeEntity;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeDTO;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypePredicate;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeRequestDto;
import com.gmf.user_management.masterData.licenseType.dto.LicenseTypeResponDTO;
import com.gmf.user_management.masterData.licenseType.entities.LicenseTypeEntity;
import com.gmf.user_management.masterData.licenseType.repository.LicenseTypeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LicenseTypeService {
    @Autowired
    private LicenseTypeRespository licenseTypeRespository;

    private LicenseTypeResponDTO licenseTypeRespon(LicenseTypeEntity licenseTypeEntity) {
        return LicenseTypeResponDTO.builder()
                .idLicenseType(licenseTypeEntity.getIdLicenseType())
                .licenseName(licenseTypeEntity.getLicenseName())
                .createdAt(licenseTypeEntity.getCreatedAt())
                .createdBy(licenseTypeEntity.getCreatedBy())
                .updatedAt(licenseTypeEntity.getUpdatedAt())
                .updatedBy(licenseTypeEntity.getUpdatedBy())
                .build();
    }
    
    public LicenseTypeResponDTO createLicenseType(LicenseTypeDTO request) {
        LicenseTypeEntity licenseType = new LicenseTypeEntity();
        LicenseTypeEntity payload = licenseTypePayload(request, licenseType);
        licenseTypeRespository.save(payload);
        return licenseTypeRespon(payload);
    }

    public LicenseTypeResponDTO updateLicenseType(Long idLicenseType, LicenseTypeDTO request) {
        LicenseTypeEntity licenseType = licenseTypeRespository.findById(idLicenseType).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        LicenseTypeEntity payload = licenseTypePayload(request, licenseType);
        licenseTypeRespository.saveAndFlush(payload);
        return licenseTypeRespon(payload);
    }

    public Boolean deleteLicenseType(Long idLicenseType) {
        licenseTypeRespository.findById(idLicenseType);
        return true;
    }

    public PaginationUtil<LicenseTypeEntity, LicenseTypeDTO> getAllLicenseType(Integer page, Integer size, LicenseTypeRequestDto requestDto) {
        Pageable paging = PageRequest.of(page -1, size);
        Specification<LicenseTypeEntity> specs = Specification
                .where(LicenseTypePredicate.searchTerm(requestDto.getSearchTerm()));
        Page<LicenseTypeEntity> pages = licenseTypeRespository.findAll(specs, paging);
        return new PaginationUtil<>(pages, LicenseTypeDTO.class);
    }

    private LicenseTypeEntity licenseTypePayload(LicenseTypeDTO request, LicenseTypeEntity licenseTypeEntity) {
        licenseTypeEntity.setLicenseName(request.getLicenseName());
        licenseTypeEntity.setCreatedBy(request.getCreatedBy());
        licenseTypeEntity.setUpdatedBy(request.getUpdatedBy());
        return licenseTypeEntity;
    }

    public LicenseTypeResponDTO getLicenseTypeById(Long id_license_type) throws NotFoundException {
        LicenseTypeEntity licenseType = JpaResultHelperUtil.getSingleResultFromOptional(licenseTypeRespository.findById(id_license_type));
        if (licenseType == null){
            throw new NotFoundException("id not found");
        }
        return ObjectMapperUtil.map(licenseType, LicenseTypeResponDTO.class);
    }
}
