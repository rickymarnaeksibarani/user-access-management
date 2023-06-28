package com.gmf.user_management.domains.vendor;

import com.gmf.user_management.core.dto.HttpResponseDTO;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.validations.IsNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/vendor")
public class VendorControllerImpl implements VendorController {

    @Autowired
    private VendorService vendorService;

    @Override
    @GetMapping
    public ResponseEntity<?> getPaginatedVendor(
        @RequestParam(defaultValue = "1") @IsNumeric String page,
        @RequestParam(defaultValue = "20") @IsNumeric String perPage,
        VendorDTO vendorRequest
    ) {
        return new HttpResponseDTO<>(vendorService.getPaginatedVendor(Integer.parseInt(page), Integer.parseInt(perPage), vendorRequest))
            .setResponseHeaders("request", vendorRequest)
            .setResponseHeaders("page", page)
            .setResponseHeaders("perPage", perPage)
            .toResponse("Getting Vendor Paginated from Server");
    }

    @Override
    @GetMapping("/{vendorId}")
    public ResponseEntity<?> getVendorDetailById(@PathVariable Long vendorId) throws NotFoundException {
        return new HttpResponseDTO<>(vendorService.getVendorDetailById(vendorId))
            .setResponseHeaders("vendorId", vendorId)
            .toResponse("Getting Vendor Detail By ID from Server");
    }

    @Override
    @PutMapping("/{vendorId}")
    public ResponseEntity<?> updateVendorById(@PathVariable Long vendorId, @RequestBody @Valid VendorDTO vendorRequest) throws NotFoundException {
        return new HttpResponseDTO<>(vendorService.updateVendorById(vendorId, vendorRequest))
            .setResponseHeaders("vendorId", vendorId)
            .setResponseHeaders("vendorRequest", vendorRequest)
            .toResponse("Update Vendor Detail By ID from Server");
    }

    @Override
    @PostMapping
    public ResponseEntity<?> createNewVendor(VendorDTO vendorRequest) {
        return new HttpResponseDTO<>(vendorService.createNewVendor(vendorRequest))
            .setResponseHeaders("vendorRequest", vendorRequest)
            .toResponse("Store New Vendor");
    }

    @Override
    @DeleteMapping("/{vendorId}")
    public ResponseEntity<?> deleteVendorById(@PathVariable Long vendorId) throws NotFoundException {
        return new HttpResponseDTO<>(vendorService.deleteVendorById(vendorId))
            .setResponseHeaders("vendorId", vendorId)
            .toResponse("Delete Vendor By ID");
    }
}
