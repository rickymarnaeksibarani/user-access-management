package com.gmf.user_management.masterData.jobCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodePredicate;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeRequestDto;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeResponeDTO;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.jobCode.repositories.JobCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobCodeService {
    @Autowired
    private JobCodeRepository jobCodeRepository;


    private JobCodeResponeDTO jobRespone(JobCodeEntity jobCodeEntity)throws JsonProcessingException{
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
    public JobCodeResponeDTO createJobCode(JobCodeDTO request)throws Exception{
        JobCodeEntity jobCode = new JobCodeEntity();
        JobCodeEntity payload = jobCodePayload(request, jobCode);
        jobCodeRepository.save(payload);
        return jobRespone(payload);
    }

    public JobCodeResponeDTO updateJobCode(Long id_job_code, JobCodeDTO request) throws Exception {
        JobCodeEntity jobCode = jobCodeRepository.findById(id_job_code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
        JobCodeEntity payload = jobCodePayload(request, jobCode);
        jobCodeRepository.saveAndFlush(payload);
        return jobRespone(payload);
    }


    private JobCodeEntity jobCodePayload(JobCodeDTO request, JobCodeEntity jobCode) {
        jobCode.setJobPosition(request.getJobPosition());
        jobCode.setJobCode(request.getJobCode());
        jobCode.setCreatedBy(request.getCreatedBy());
        jobCode.setUpdatedBy(request.getUpdatedBy());
        return jobCode;
    }


    public Boolean deleteJobCode(Long idJobCode) {
        jobCodeRepository.deleteById(idJobCode);
        return true;
    }

    public PaginationUtil<JobCodeEntity, JobCodeResponeDTO> getAllJobCode(
            Integer page, Integer size, JobCodeRequestDto requestDto
    ){
        Pageable paging = PageRequest.of(page -1, size);
        Specification<JobCodeEntity> specs = Specification
                .where(JobCodePredicate.searchTerm(requestDto.getSearchTerm()));
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
