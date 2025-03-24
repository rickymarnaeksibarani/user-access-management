package com.gmf.user_management.modules.jobCode.repositories;

import com.gmf.user_management.modules.jobCode.entities.JobCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobCodeRepository extends JpaRepository<JobCodeEntity, Long>, JpaSpecificationExecutor<JobCodeEntity> {
    List<JobCodeEntity> findByIdJobCodeIsIn(Collection<Long> id);

    boolean existsByJobCode(@NotEmpty String jobCode);

    Optional<JobCodeEntity> findByJobCode(@NotEmpty String jobCode);
}
