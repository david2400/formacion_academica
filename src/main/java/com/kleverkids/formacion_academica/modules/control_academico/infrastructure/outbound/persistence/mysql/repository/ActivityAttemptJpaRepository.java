package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * {@code assignmentId} es nullable (práctica libre sin asignación formal), por
 * lo que las consultas que lo filtran usan JPQL explícito en vez de derived
 * queries: un derived query {@code findByAssignmentId} con un parámetro
 * {@code null} no compila a "IS NULL" de forma confiable.
 */
public interface ActivityAttemptJpaRepository extends JpaRepository<ActivityAttemptEntity, Long> {

    @Query("SELECT a FROM ActivityAttemptEntity a WHERE a.activityId = :activityId "
            + "AND ((:assignmentId IS NULL AND a.assignmentId IS NULL) OR a.assignmentId = :assignmentId) "
            + "AND a.estudianteId = :estudianteId AND a.estado = :estado")
    Optional<ActivityAttemptEntity> findEnProgreso(@Param("activityId") Long activityId,
                                                    @Param("assignmentId") Long assignmentId,
                                                    @Param("estudianteId") Long estudianteId,
                                                    @Param("estado") EstadoActivityAttempt estado);

    @Query("SELECT COUNT(a) FROM ActivityAttemptEntity a WHERE a.activityId = :activityId "
            + "AND ((:assignmentId IS NULL AND a.assignmentId IS NULL) OR a.assignmentId = :assignmentId) "
            + "AND a.estudianteId = :estudianteId AND a.estado IN :estados")
    int contarPorEstados(@Param("activityId") Long activityId,
                          @Param("assignmentId") Long assignmentId,
                          @Param("estudianteId") Long estudianteId,
                          @Param("estados") List<EstadoActivityAttempt> estados);

    List<ActivityAttemptEntity> findByEstudianteIdAndActivityIdOrderByAttemptNumberDesc(Long estudianteId, Long activityId);
}
