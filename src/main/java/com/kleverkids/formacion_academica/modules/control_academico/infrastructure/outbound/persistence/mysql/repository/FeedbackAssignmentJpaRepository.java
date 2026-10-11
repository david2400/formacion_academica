package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackAssignmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackAssignmentJpaRepository extends JpaRepository<FeedbackAssignmentEntity, Long> {

    @Query("""
            SELECT a FROM FeedbackAssignmentEntity a
            WHERE (:formId IS NULL OR a.formId = :formId)
              AND (:claseId IS NULL OR a.claseId = :claseId)
              AND (:grupoId IS NULL OR a.grupoId = :grupoId)
              AND (:estadoId IS NULL OR a.estadoId = :estadoId)
            """)
    Page<FeedbackAssignmentEntity> buscar(@Param("formId") Long formId, @Param("claseId") Long claseId,
                                          @Param("grupoId") Long grupoId, @Param("estadoId") Long estadoId,
                                          Pageable pageable);
}
