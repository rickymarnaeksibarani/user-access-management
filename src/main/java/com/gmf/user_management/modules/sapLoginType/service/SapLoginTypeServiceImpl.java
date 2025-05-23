package com.gmf.user_management.modules.sapLoginType.service;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.personal.repository.PersonalRepository;
import com.gmf.user_management.modules.sapLoginType.dto.SapLoginTypeDTO;
import com.gmf.user_management.modules.sapLoginType.dto.SapLoginTypePredicate;
import com.gmf.user_management.modules.sapLoginType.dto.SapLoginTypeRequest;
import com.gmf.user_management.modules.sapLoginType.dto.SapLoginTypeResponDTO;
import com.gmf.user_management.modules.sapLoginType.entities.SapLoginTypeEntity;
import com.gmf.user_management.modules.sapLoginType.repository.SapLoginTypeRepository;
import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SapLoginTypeServiceImpl implements SapLoginTypeService{

    private final SapLoginTypeRepository sapLoginTypeRepository;
    private final PersonalRepository personalRepository;

    private SapLoginTypeResponDTO sapLoginTypeResponDTO(SapLoginTypeEntity sapLoginTypeEntity){
        return SapLoginTypeResponDTO.builder()
                .idSapLoginType(sapLoginTypeEntity.getIdSapLoginType())
                .loginType(sapLoginTypeEntity.getLoginType())
                .createdAt(sapLoginTypeEntity.getCreatedAt())
                .createdBy(sapLoginTypeEntity.getCreatedBy())
                .updatedAt(sapLoginTypeEntity.getUpdatedAt())
                .updatedBy(sapLoginTypeEntity.getUpdatedBy())
                .build();
    }

    @Override
    public SapLoginTypeResponDTO createSapLoginType(SapLoginTypeDTO requestDto) {
        try {

            SapLoginTypeEntity data = new SapLoginTypeEntity();
            SapLoginTypeEntity payload = sapLoginTypePayload(requestDto, data);
            sapLoginTypeRepository.save(payload);
            return sapLoginTypeResponDTO(payload);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SapLoginTypeResponDTO updateSapLoginType(Long idSapLoginType, SapLoginTypeDTO requestDto) throws NotFoundException {
        try {
            SapLoginTypeEntity data = sapLoginTypeRepository.findById(idSapLoginType).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
            SapLoginTypeEntity payload = sapLoginTypePayload(requestDto, data);
            sapLoginTypeRepository.saveAndFlush(payload);
            return sapLoginTypeResponDTO(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteSapLoginType(Long idSapLoginType){
        Optional<SapLoginTypeEntity> find = sapLoginTypeRepository.findById(idSapLoginType);
        if (find.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SAP Login Type with id "+ idSapLoginType + " is not found");

        SapLoginTypeEntity sapLogin = find.get();
        List<PersonalEntity> sapLoginRelation = personalRepository.findBySapLoginTypeListContaining(sapLogin);
        if (!sapLoginRelation.isEmpty())throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete SAP Login Type because it is still used in another table");


        sapLoginTypeRepository.deleteById(idSapLoginType);
        return true;
    }

    @Override
    public PaginationUtil<SapLoginTypeEntity, SapLoginTypeResponDTO> getAllSapLoginType(Integer page, Integer size, SapLoginTypeRequest requestDTO) {
        Pageable paging = PageRequest.of(page -1, size, Sort.by(Sort.Order.asc("createdAt")));
        Specification<SapLoginTypeEntity> specs = Specification.where(SapLoginTypePredicate.filterSapLoginType(requestDTO.getFilterSapLoginType()));
        Page<SapLoginTypeEntity> pages = sapLoginTypeRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, SapLoginTypeResponDTO.class);
    }
    
    @Override
    public SapLoginTypeResponDTO getSapLoginTypeById(Long idSapLoginType) throws NotFoundException {
        SapLoginTypeEntity sapLoginTypeEntity = sapLoginTypeRepository.findById(idSapLoginType).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return sapLoginTypeResponDTO(sapLoginTypeEntity);
    }

    private SapLoginTypeEntity sapLoginTypePayload(SapLoginTypeDTO requestDto, SapLoginTypeEntity sapLoginTypeEntity){
        boolean exists = sapLoginTypeRepository.existsByLoginType(requestDto.getLoginType());
        if (exists){throw new ResponseStatusException(HttpStatus.CONFLICT, "SAP Login Type with " + requestDto.getLoginType() + " is already exists");}
        sapLoginTypeEntity.setLoginType(requestDto.getLoginType());
        sapLoginTypeEntity.setCreatedBy(requestDto.getCreatedBy());
        sapLoginTypeEntity.setUpdatedBy(requestDto.getUpdatedBy());
        return sapLoginTypeEntity;
    }

}
