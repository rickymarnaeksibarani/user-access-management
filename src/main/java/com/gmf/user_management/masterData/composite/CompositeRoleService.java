package com.gmf.user_management.masterData.composite;

import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRolePredicate;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleRequestDTO;
import com.gmf.user_management.masterData.composite.compositeDto.CompositeRoleResponDTO;
import com.gmf.user_management.masterData.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.jobCode.repositories.JobCodeRepository;
import com.gmf.user_management.masterData.unit.dto.UnitPredicate;
import com.gmf.user_management.masterData.unit.dto.UnitRequestDto;
import com.gmf.user_management.masterData.unit.dto.UnitResponDto;
import com.gmf.user_management.masterData.unit.entities.UnitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompositeRoleService {

    @Autowired
    private CompositeRoleRepository compositeRoleRepository;
    @Autowired
    private JobCodeRepository jobCodeRepository;

    private CompositeRoleResponDTO compositeRoleRespon(CompositeRoleEntity compositeRoleEntity){
        return CompositeRoleResponDTO
                .builder()
                .idCompositeRole(compositeRoleEntity.getIdCompositeRole())
                .jobCodeEntityList(compositeRoleEntity.getJobCodeEntityList())
                .compositeRole(compositeRoleEntity.getCompositeRole())
                .createdAt(compositeRoleEntity.getCreatedAt())
                .createdBy(compositeRoleEntity.getCreatedBy())
                .updatedAt(compositeRoleEntity.getUpdatedAt())
                .updatedBy(compositeRoleEntity.getUpdatedBy())
                .build();
    }
    public CompositeRoleResponDTO createCompositeRole(CompositeRoleDTO request) {
        CompositeRoleEntity compositeRole = new CompositeRoleEntity();
        CompositeRoleEntity payload = compositePayload(request, compositeRole);
        compositeRoleRepository.save(payload);
        return compositeRoleRespon(payload);
    }

    public CompositeRoleResponDTO updateCompositeRole(Long idCompositeRole,CompositeRoleDTO request){
        CompositeRoleEntity data = compositeRoleRepository.findById(idCompositeRole).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        CompositeRoleEntity payload = compositePayload(request, data);
        compositeRoleRepository.save(payload);
        return compositeRoleRespon(payload);
    }

    public Boolean deleteCompositeRole(Long idCompositeRole) {
        compositeRoleRepository.findById(idCompositeRole);
        return true;
    }

    private CompositeRoleEntity compositePayload(CompositeRoleDTO request, CompositeRoleEntity compositeRole) {
        List<JobCodeEntity> allJobCode = jobCodeRepository.findByIdJobCodeIsIn(request.getJobCodeEntityList());
        if (allJobCode.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        compositeRole.setJobCodeEntityList(allJobCode);
        compositeRole.setCompositeRole(request.getCompositeRole());
        compositeRole.setCreatedBy(request.getCreatedBy());
        compositeRole.setUpdatedBy(request.getUpdatedBy());
        return compositeRole;
    }

    public PaginationUtil<CompositeRoleEntity, CompositeRoleResponDTO> getAllCompositeRole(Integer page, Integer size, CompositeRoleRequestDTO requestDto) {
        Pageable paging = PageRequest.of(page -1 ,size);
        Specification<CompositeRoleEntity> specs = Specification.where(CompositeRolePredicate.searchTerm(requestDto.getSearchTerm()));
        Page<CompositeRoleEntity> pages = compositeRoleRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, CompositeRoleResponDTO.class);
    }

    public CompositeRoleResponDTO getCompositeRoleById(Long idCompositeRole) {
        CompositeRoleEntity data = JpaResultHelperUtil.getSingleResultFromOptional(compositeRoleRepository.findById(idCompositeRole));
        if (data == null)throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        return ObjectMapperUtil.map(data, CompositeRoleResponDTO.class);
    }
    public Long countCompositeRoleByJobCodeId(Long jobCodeId) {
        return compositeRoleRepository.countByJobCodeEntityList(jobCodeId);
    }

}
