package com.gmf.user_management.masterData.personal.repository;

import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<PersonalEntity, Long>, JpaSpecificationExecutor<PersonalEntity> {
}
