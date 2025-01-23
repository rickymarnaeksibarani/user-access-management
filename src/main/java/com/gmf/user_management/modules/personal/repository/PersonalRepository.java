package com.gmf.user_management.modules.personal.repository;

import com.gmf.user_management.modules.personal.entities.PersonalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<PersonalEntity, Long>, JpaSpecificationExecutor<PersonalEntity>{
    List<PersonalEntity> findByIdPersonalIsIn(Collection<Long> id);

    Optional<Object> findByPersonalNumber(String personalNumber);

    Page<PersonalEntity> findAllByDinas(String dinas, Pageable pageable);

    @Query("SELECT p.dinas AS dinas, COUNT(DISTINCT p.uid) AS uidCount FROM PersonalEntity p GROUP BY p.dinas")
    List<Map<String, Object>> countUIDByDinas();

    @Query("SELECT p FROM PersonalEntity p WHERE p.partnerExternal = :partnerExternal AND p.isPic = true")
    Page<PersonalEntity> findAllPersonalAsPartnerPIC(@Param("partnerExternal") Long partnerExternal, Pageable pageable);

    Page<PersonalEntity> findBySapLoginTypeList_IdSapLoginType(Long sapLoginTypeId, Pageable paging);


}
