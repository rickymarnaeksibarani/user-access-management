package com.gmf.user_management.masterData.jobCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.core.exceptions.NotFoundException;
import com.gmf.user_management.core.utils.JpaResultHelperUtil;
import com.gmf.user_management.core.utils.ObjectMapperUtil;
import com.gmf.user_management.core.utils.PaginationUtil;
import com.gmf.user_management.domains.user.UserPaginationRequest;
import com.gmf.user_management.domains.user.UserPredicate;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class JobCodeService {
    @Autowired
    private JobCodeRepository jobCodeRepository;


    private JobCodeResponeDTO jobRespone(JobCodeEntity jobCodeEntity)throws JsonProcessingException{
        return JobCodeResponeDTO.builder()
                .id_job_code(jobCodeEntity.getId_job_code())
                .job_position(jobCodeEntity.getJob_code())
                .job_code(jobCodeEntity.getJob_code())
                .created_at(jobCodeEntity.getCreated_at())
                .created_by(jobCodeEntity.getCreated_by())
                .updated_at(jobCodeEntity.getUpdated_at())
                .update_by(jobCodeEntity.getUpdated_by())
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
        jobCodeRepository.save(payload);
        return jobRespone(payload);
    }


    private JobCodeEntity jobCodePayload(JobCodeDTO request, JobCodeEntity jobCode) {
        jobCode.setJob_position(request.getJob_position());
        jobCode.setJob_code(request.getJob_code());
        jobCode.setCreated_by(request.getCreated_by());
        jobCode.setUpdated_by(request.getUpdated_by());
        return jobCode;
    }


    public Boolean deleteJobCode(Long idJobCode) {
        jobCodeRepository.deleteById(idJobCode);
        return true;
    }

    public PaginationUtil<JobCodeEntity, JobCodeDTO> getAllJobCode(
            Integer page, Integer size, JobCodeRequestDto requestDto
    ){
        Pageable paging = PageRequest.of(page -1, size);
        Specification<JobCodeEntity> specs = Specification
                .where(JobCodePredicate.searchTerm(requestDto.getSearchTerm()));
        Page<JobCodeEntity> pages = jobCodeRepository.findAll(specs, paging);
        return new PaginationUtil<>(pages, JobCodeDTO.class);
    }


    public JobCodeResponeDTO getJobCodeById(Long idJobCode)throws NotFoundException {
        JobCodeEntity jobCode = JpaResultHelperUtil.getSingleResultFromOptional(jobCodeRepository.findById(idJobCode));
        if (jobCode == null){
            throw new NotFoundException("id not found");
        }
        return ObjectMapperUtil.map(jobCode, JobCodeResponeDTO.class);
    }
}
