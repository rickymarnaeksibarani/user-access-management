package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface VendorController {
    ResponseEntity<?> getPaginatedVendor(String perPage, String page, VendorDTO vendorRequest);

    ResponseEntity<?> getVendorDetailById(Long vendorId) throws NotFoundException;

    ResponseEntity<?> updateVendorById(Long vendorId, VendorDTO vendorRequest) throws NotFoundException;

    ResponseEntity<?> createNewVendor(VendorDTO vendorRequest);

    ResponseEntity<?> deleteVendorById(Long vendorId) throws NotFoundException;

}
