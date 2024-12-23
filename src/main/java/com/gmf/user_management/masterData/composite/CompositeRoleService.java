package com.gmf.user_management.masterData.composite;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRolePredicate;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleRequestDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleResponDTO;
import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.masterData.composite.repository.CompositeRoleRepository;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.jobCode.repositories.JobCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class CompositeRoleService {

    @Autowired
    private CompositeRoleRepository compositeRoleRepository;
    @Autowired
    private JobCodeRepository jobCodeRepository;

    private CompositeRoleResponDTO compositeRoleRespon(CompositeRoleEntity compositeRoleEntity){
        return CompositeRoleResponDTO
                .builder()
                .idCompositeRole(compositeRoleEntity.getIdCompositeRole())
                .jobCodeList(compositeRoleEntity.getJobCodeList())
                .compositeRole(compositeRoleEntity.getCompositeRole())
                .createdAt(compositeRoleEntity.getCreatedAt())
                .createdBy(compositeRoleEntity.getCreatedBy())
                .updatedAt(compositeRoleEntity.getUpdatedAt())
                .updatedBy(compositeRoleEntity.getUpdatedBy())
                .jobCodeCount((compositeRoleEntity.getJobCodeList() != null ? compositeRoleEntity.getJobCodeList().size() : 0))
                .build();
    }
    public CompositeRoleResponDTO createCompositeRole(CompositeRoleDTO request) {
        CompositeRoleEntity compositeRole = new CompositeRoleEntity();
        CompositeRoleEntity payload = compositePayload(request, compositeRole);
        compositeRoleRepository.save(payload);
        return compositeRoleRespon(payload);
    }

    public CompositeRoleResponDTO updateCompositeRole(
            Long idCompositeRole,CompositeRoleDTO request) {
        CompositeRoleEntity data = compositeRoleRepository.findById(idCompositeRole).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        CompositeRoleEntity payload = compositePayload(request, data);
        compositeRoleRepository.saveAndFlush(payload);
        return compositeRoleRespon(payload);


    }

    public Boolean deleteCompositeRole(Long idCompositeRole) {
        compositeRoleRepository.deleteById(idCompositeRole);
        return true;
    }
    private CompositeRoleEntity compositePayload(CompositeRoleDTO request, CompositeRoleEntity compositeRole) {
        List<JobCodeEntity> allJobCode = jobCodeRepository.findByIdJobCodeIsIn(request.getJobCodeList());
        if (allJobCode.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Job Code not found");
        compositeRole.setJobCodeList(allJobCode);
        compositeRole.setCompositeRole(request.getCompositeRole());
        compositeRole.setCreatedBy(request.getCreatedBy());
        compositeRole.setUpdatedBy(request.getUpdatedBy());
        return compositeRole;
    }
    public PaginationUtil<CompositeRoleEntity, CompositeRoleEntity> getAllCompositeRole(Integer page, Integer size, CompositeRoleRequestDTO requestDto) {
        Pageable paging = PageRequest.of(page - 1, size);
        Specification<CompositeRoleEntity> specs = Specification.where(CompositeRolePredicate.searchTerm(requestDto.getSearchTerm()));
        Page<CompositeRoleEntity> pages = compositeRoleRepository.findAll(specs, paging);
        List<CompositeRoleResponDTO> responseDTOs = pages.getContent().stream()
                .map(entity -> {
                    CompositeRoleResponDTO dto = ObjectMapperUtil.map(entity, CompositeRoleResponDTO.class);
                    dto.setJobCodeCount(entity.getJobCodeList() != null ? entity.getJobCodeList().size() : 0);
                    return dto;
                })
                .toList();

        return new PaginationUtil<>(pages, CompositeRoleEntity.class);
    }

    public CompositeRoleResponDTO getCompositeRoleById(Long id_composite_role) throws NotFoundException {
        CompositeRoleEntity businessUnitCode = JpaResultHelperUtil.getSingleResultFromOptional(compositeRoleRepository.findById(id_composite_role));
        if (businessUnitCode == null){
            throw new NotFoundException("id not found");
        }
        CompositeRoleResponDTO responseDTO = ObjectMapperUtil.map(businessUnitCode, CompositeRoleResponDTO.class);
        responseDTO.setJobCodeCount(businessUnitCode.getJobCodeList() != null ? businessUnitCode.getJobCodeList().size() : 0);
        return responseDTO;
    }

    public PaginationUtil<CompositeRoleEntity, CompositeRoleEntity> getCompositeRoleByJobCodeId(Long jobCodeId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<CompositeRoleEntity> compositeRolesPage = compositeRoleRepository.findByJobCodeList_idJobCode(jobCodeId, paging);
        return new PaginationUtil<>(compositeRolesPage, CompositeRoleEntity.class);
    }

    public Long countCompositeRoleByJobCodeId(Long jobCodeId) {
        boolean jobCodeExists = jobCodeRepository.existsById(jobCodeId);
        if (!jobCodeExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "JobCodeId: " + jobCodeId + " not found");
        }
        return compositeRoleRepository.countByJobCodeList_idJobCode(jobCodeId);
    }
}
