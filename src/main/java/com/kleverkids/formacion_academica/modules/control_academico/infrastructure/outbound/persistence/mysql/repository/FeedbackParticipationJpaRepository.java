package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackParticipationEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FeedbackParticipationJpaRepository extends JpaRepository<FeedbackParticipationEntity, FeedbackParticipationId> {

    /** Cuántas veces respondió un estudiante una clase en una aplicación (para calcular el siguiente número). */
    long countById_AssignmentIdAndId_ClaseIdAndId_EstudianteId(Long assignmentId, Long claseId, Long estudianteId);

    /** Pares (aplicación, clase) ya respondidos por el estudiante dentro de un conjunto de aplicaciones. */
    @Query("""
            SELECT DISTINCT p.id.assignmentId, p.id.claseId FROM FeedbackParticipationEntity p
            WHERE p.id.estudianteId = :estudianteId AND p.id.assignmentId IN :assignmentIds
            """)
    List<Object[]> respondidas(@Param("estudianteId") Long estudianteId,
                               @Param("assignmentIds") Collection<Long> assignmentIds);
}
