package com.gmf.user_management.modules.jobCode.repositories;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JobCodeRepository extends JpaRepository<JobCodeEntity, Long>, JpaSpecificationExecutor<JobCodeEntity> {
    List<JobCodeEntity> findByIdJobCodeIsIn(Collection<Long> id);
}
