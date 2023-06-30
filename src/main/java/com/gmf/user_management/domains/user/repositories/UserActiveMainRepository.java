package com.gmf.user_management.domains.user.repositories;

import com.gmf.user_management.domains.user.entities.UserActiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActiveMainRepository extends JpaRepository<UserActiveEntity, Long>, JpaSpecificationExecutor<UserActiveEntity> {}
