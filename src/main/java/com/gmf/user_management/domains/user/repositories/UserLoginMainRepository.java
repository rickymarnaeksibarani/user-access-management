package com.gmf.user_management.domains.user.repositories;

import com.gmf.user_management.domains.user.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginMainRepository extends JpaRepository<UserLoginEntity, Long>, JpaSpecificationExecutor<UserLoginEntity> {}
