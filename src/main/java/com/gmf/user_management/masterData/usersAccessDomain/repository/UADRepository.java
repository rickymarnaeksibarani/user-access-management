package com.gmf.user_management.masterData.usersAccessDomain.repository;

import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import com.gmf.user_management.masterData.usersAccessDomain.entities.UserAccessDomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UADRepository extends JpaRepository<UserAccessDomainEntity, Long>, JpaSpecificationExecutor<UserAccessDomainEntity> {
    Optional<UserAccessDomainEntity> findByPersonalListContaining(PersonalEntity personal);
}
