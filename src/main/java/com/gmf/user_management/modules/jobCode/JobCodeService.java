package com.gmf.user_management.modules.jobCode;

import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.modules.composite.compositeEntities.CompositeRoleEntity;
import com.gmf.user_management.modules.composite.repository.CompositeRoleRepository;
import com.gmf.user_management.modules.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.modules.jobCode.dto.JobCodePredicate;
import com.gmf.user_management.modules.jobCode.dto.JobCodeRequestDto;
import com.gmf.user_management.modules.jobCode.dto.JobCodeResponeDTO;
import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.modules.jobCode.repositories.JobCodeRepository;
import com.gmf.user_management.modules.unitJobCode.entities.UnitJobCodeEntity;
import com.gmf.user_management.modules.unitJobCode.repository.UnitJobCodeRepository;
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
public class JobCodeService {

    private final JobCodeRepository jobCodeRepository;
    private final UnitJobCodeRepository unitJobCodeRepository;
    private final CompositeRoleRepository compositeRoleRepository;

    private JobCodeResponeDTO jobRespone(JobCodeEntity jobCodeEntity){
        return JobCodeResponeDTO.builder()
                .idJobCode(jobCodeEntity.getIdJobCode())
                .jobPosition(jobCodeEntity.getJobPosition())
                .jobCode(jobCodeEntity.getJobCode())
                .createdAt(jobCodeEntity.getCreatedAt())
                .createdBy(jobCodeEntity.getCreatedBy())
                .updatedAt(jobCodeEntity.getUpdatedAt())
                .updatedBy(jobCodeEntity.getUpdatedBy())
                .build();
    }
    public JobCodeResponeDTO createJobCode(JobCodeDTO request){
        try {
            JobCodeEntity jobCode = new JobCodeEntity();
            JobCodeEntity payload = jobCodePayload(request, jobCode);
            jobCodeRepository.save(payload);
            return jobRespone(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public JobCodeResponeDTO updateJobCode(Long id_job_code, JobCodeDTO request){
        JobCodeEntity jobCode = jobCodeRepository.findById(id_job_code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
        JobCodeEntity payload = jobCodePayload(request, jobCode);
        jobCodeRepository.saveAndFlush(payload);
        return jobRespone(payload);
    }


    private JobCodeEntity jobCodePayload(JobCodeDTO request, JobCodeEntity jobCode) {
//        boolean existsByJobCode = jobCodeRepository.existsByJobCode(request.getJobCode());
//        if (existsByJobCode){throw new ResponseStatusException(HttpStatus.CONFLICT, "Job Code is already exists");}
//        Optional<JobCodeEntity> existingEntity = jobCodeRepository.findByJobCode(request.getJobCode());
//
//        if (existingEntity.isPresent() && !existingEntity.get().getIdJobCode().equals(jobCode.getIdJobCode())) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Job Code is already exists with another ID");
//        }
        jobCode.setJobPosition(request.getJobPosition());
        jobCode.setJobCode(request.getJobCode());
        jobCode.setCreatedBy(request.getCreatedBy());
        jobCode.setUpdatedBy(request.getUpdatedBy());
        return jobCode;
    }


    public Boolean deleteJobCode(Long idJobCode) {
        Optional<JobCodeEntity> jobCodeOpt = jobCodeRepository.findById(idJobCode);
        if (jobCodeOpt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Code with id: " + idJobCode + " is not found");

        JobCodeEntity jobCode =  jobCodeOpt.get();
        List<UnitJobCodeEntity> jobCodeRelations =  unitJobCodeRepository.findByJobCodeListContaining(jobCode);
        if (!jobCodeRelations.isEmpty())throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete job code because it is still used in another table");

        List<CompositeRoleEntity> compositeRoleRelation =  compositeRoleRepository.findByJobCodeListContaining(jobCode);
        for (CompositeRoleEntity composite : compositeRoleRelation)
        {
            compositeRoleRepository.delete(composite);
        }

        jobCodeRepository.deleteById(idJobCode);
        return true;
    }

    public PaginationUtil<JobCodeEntity, JobCodeResponeDTO> getAllJobCode(
            Integer page, Integer size, JobCodeRequestDto requestDto
    ){
        Pageable paging = PageRequest.of(page -1, size, Sort.by(Sort.Order.asc("createdAt")));
        Specification<JobCodeEntity> specs = Specification
                .where(JobCodePredicate.searchJobCode(requestDto.getSearchJobCode()))
                .and(JobCodePredicate.filterByJobPosition(requestDto.getFilterByJobPosition()));
        Page<JobCodeEntity> pages = jobCodeRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, JobCodeResponeDTO.class);
    }


    public JobCodeResponeDTO getJobCodeById(Long idJobCode)throws NotFoundException {
        JobCodeEntity jobCode = JpaResultHelperUtil.getSingleResultFromOptional(jobCodeRepository.findById(idJobCode));
        if (jobCode == null){
            throw new NotFoundException("id not found");
        }
        return ObjectMapperUtil.map(jobCode, JobCodeResponeDTO.class);
    }
}
