package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorMainRepository vendorMainRepository;

    @Override
    public PaginationUtil<VendorEntity, VendorDTO> getPaginatedVendor(Integer page, Integer perPage, VendorDTO vendorRequest) {
        Pageable paging = PageRequest.of(page - 1, perPage);

        Specification<VendorEntity> specs = Specification
                .where(VendorPredicate.withSearchTerm(vendorRequest.getCompanyName()))
                ;

        Page<VendorEntity> pagedVendors = vendorMainRepository.findAll(specs, paging);

        return new PaginationUtil<>(pagedVendors, VendorDTO.class);
    }

    @Override
    public VendorDTO getVendorDetailById(Long vendorId) throws NotFoundException {
        if(!vendorMainRepository.existsById(vendorId)) {
            throw new NotFoundException("Vendor Not Found");
        }

        Optional<VendorEntity> vendorOptional = vendorMainRepository.findById(vendorId);

        VendorEntity vendor = JpaResultHelperUtil.getSingleResultFromOptional(vendorOptional);

        return ObjectMapperUtil.map(vendor, VendorDTO.class);
    }

    @Override
    @Transactional
    public VendorDTO updateVendorById(Long vendorId, VendorDTO vendorRequest) throws NotFoundException {
        if(!vendorMainRepository.existsById(vendorId)) {
            throw new NotFoundException("Vendor Not Found");
        }

        vendorRequest.setIdUserSource(vendorId);

        return upsertVendorEntity(vendorRequest);
    }

    @Override
    @Transactional
    public VendorDTO createNewVendor(VendorDTO vendorRequest) {
        return upsertVendorEntity(vendorRequest);
    }

    @Override
    @Transactional
    public String deleteVendorById(Long vendorId) throws NotFoundException {
        if(!vendorMainRepository.existsById(vendorId)) {
            throw new NotFoundException("Vendor Not Found");
        }

        vendorMainRepository.deleteById(vendorId);

        return "Successfully deleted Vendor";
    }

    @Transactional
    private VendorDTO upsertVendorEntity(VendorDTO vendorData) {
        VendorEntity newVendor = vendorMainRepository.saveAndFlush(ObjectMapperUtil.map(vendorData, VendorEntity.class));

        System.out.println("newVendor => " + newVendor);

        return ObjectMapperUtil.map(newVendor, VendorDTO.class);
    }
}
