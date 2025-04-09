package com.gmf.user_management.modules.usersAccessDomain.repository;

import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import com.gmf.user_management.modules.usersAccessDomain.entities.UserAccessDomainEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccessDomainRepository extends JpaRepository<UserAccessDomainEntity, Long>, JpaSpecificationExecutor<UserAccessDomainEntity> {
    Optional<UserAccessDomainEntity> findByPersonalListContaining(PersonalEntity personal);
//    List<UserAccessDomainEntity> findByPersonalListIdIn(@NonNull List<Long> personalList);
    List<UserAccessDomainEntity> findByPersonalListIn(List<PersonalEntity> personalList);
}
