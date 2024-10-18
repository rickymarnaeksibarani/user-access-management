package com.gmf.user_management.masterData.jobCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeDTO;
import com.gmf.user_management.masterData.jobCode.dto.JobCodeResponeDTO;
import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import com.gmf.user_management.masterData.jobCode.repositories.JobCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


}
