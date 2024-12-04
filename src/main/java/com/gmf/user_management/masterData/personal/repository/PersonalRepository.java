package com.gmf.user_management.masterData.personal.repository;

import com.gmf.user_management.masterData.personal.entities.PersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<PersonalEntity, Long>, JpaSpecificationExecutor<PersonalEntity> {
    List<PersonalEntity> findByIdPersonalIsIn(Collection<Long> id);

    Optional<Object> findByPersonalNumber(String personalNumber);

    Optional<Object> findByDinas(String dinas);

    List<PersonalEntity> findAllByDinas(String dinas);

    List<PersonalEntity> findByDinasIsNotNull();

    @Query("SELECT p.dinas AS dinas, COUNT(DISTINCT p.uid) AS uidCount FROM PersonalEntity p GROUP BY p.dinas")
    List<Map<String, Object>> countUIDByDinas();

    List<PersonalEntity> findAllByPartnerId(Long partnerId);
}
