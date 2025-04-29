package com.gmf.user_management.modules.composite;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleDTO;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRolePredicate;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleRequestDTO;
import com.gmf.user_management.modules.composite.compositeDto.CompositeRoleResponDTO;
import com.gmf.user_management.modules.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.modules.composite.repository.CompositeRoleRepository;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.jobCode.repositories.JobCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompositeRoleService {

    private final JobCodeRepository jobCodeRepository;
    private final CompositeRoleRepository compositeRoleRepository;

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
//        boolean exists = compositeRoleRepository.existsByCompositeRole(request.getCompositeRole());
//        if (exists){throw new ResponseStatusException(HttpStatus.CONFLICT, "Composite Role is already exists");}
        List<CompositeRoleEntity> existingComposite = compositeRoleRepository.findByCompositeRole(request.getCompositeRole());
        List<Long> newJobCodeIds = allJobCode.stream()
                .map(JobCodeEntity::getIdJobCode)
                .sorted()
                .toList();

//        boolean isDuplicate = existingComposite.stream().anyMatch(role -> {
//            List<Long> existingJobCodeIds = role.getJobCodeList().stream()
//                    .map(JobCodeEntity::getIdJobCode)
//                    .sorted()
//                    .toList();
//            return existingJobCodeIds.equals(newJobCodeIds);
//        });
//
//        if (isDuplicate) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Composite Role with the same JobCode is already exists");
//        }

        compositeRole.setJobCodeList(allJobCode);
        compositeRole.setCompositeRole(request.getCompositeRole());
        compositeRole.setCreatedBy(request.getCreatedBy());
        compositeRole.setUpdatedBy(request.getUpdatedBy());
        return compositeRole;
    }

    public PaginationUtil<CompositeRoleEntity, CompositeRoleResponDTO> getAllCompositeRole(Integer page, Integer size, CompositeRoleRequestDTO requestDto) {
        Pageable paging = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("createdAt")));
        Specification<CompositeRoleEntity> specs = Specification.where(CompositeRolePredicate.searchTerm(requestDto.getSearchTerm()));
        Page<CompositeRoleEntity> pages = compositeRoleRepository.findAll(specs, paging);
        pages.getContent().stream()
                .map(entity -> {
                    CompositeRoleResponDTO dto = ObjectMapperUtil.map(entity, CompositeRoleResponDTO.class);
                    dto.setJobCodeCount(entity.getJobCodeList() != null ? entity.getJobCodeList().size() : 0);
                    return dto;
                })
                .toList();

        return new PaginationUtil<>(pages, CompositeRoleResponDTO.class);
    }

    public CompositeRoleResponDTO getCompositeRoleById(Long id_composite_role) throws NotFoundException {
        CompositeRoleEntity businessUnitCode = JpaResultHelperUtil.getSingleResultFromOptional(compositeRoleRepository.findById(id_composite_role));
        if (businessUnitCode == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id Composite Role with: " + id_composite_role + " is not found");
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
