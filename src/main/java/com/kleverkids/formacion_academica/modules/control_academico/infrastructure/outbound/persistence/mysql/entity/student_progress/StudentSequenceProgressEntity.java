package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.student_progress;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Proyección recalculable (no fuente de verdad — ver StudentSequenceProgress
 * en el dominio). No extiende AuditInfo por la misma razón que
 * ActivityAttemptAnswerEntity: no es un hecho de negocio con autor, es una
 * caché derivada que el propio sistema reescribe por completo cada vez.
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "student_sequence_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante_id", "sequence_id"}))
public class StudentSequenceProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @Column(name = "sequence_id", nullable = false)
    private Long sequenceId;

    @Column(name = "completed_items", nullable = false)
    private Integer completedItems;

    @Column(name = "total_items", nullable = false)
    private Integer totalItems;

    @Column(name = "current_item_id")
    private Long currentItemId;

    @Column(name = "percent_complete", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentComplete;

    @Column(name = "last_activity_at")
    private Instant lastActivityAt;
}
