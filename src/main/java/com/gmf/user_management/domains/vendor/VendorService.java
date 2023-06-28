package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginatorUtil;

public interface VendorService {

    PaginatorUtil<VendorEntity, VendorDTO> getPaginatedVendor(Integer page, Integer perPage, VendorDTO vendorRequest);
    VendorDTO getVendorDetailById(Long vendorId) throws NotFoundException;
    VendorDTO updateVendorById(Long vendorId, VendorDTO vendorRequest) throws NotFoundException;
    VendorDTO createNewVendor(VendorDTO vendorRequest);
    String deleteVendorById(Long vendorId) throws NotFoundException;
}
