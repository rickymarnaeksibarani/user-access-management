package com.gmf.user_management.masterData.jobCode.repositories;

import com.gmf.user_management.masterData.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCodeRepository extends JpaRepository<JobCodeEntity, Long>, JpaSpecificationExecutor<JobCodeEntity> {
    List<JobCodeEntity> findByIdJobCodeIsIn(List<Long> id);
}
