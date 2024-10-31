package com.gmf.user_management.masterData.usersAccessDomain.repository;

import com.gmf.user_management.masterData.usersAccessDomain.entities.UserAccessDomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UADRepository extends JpaRepository<UserAccessDomainEntity, Long>, JpaSpecificationExecutor<UserAccessDomainEntity> {
}
