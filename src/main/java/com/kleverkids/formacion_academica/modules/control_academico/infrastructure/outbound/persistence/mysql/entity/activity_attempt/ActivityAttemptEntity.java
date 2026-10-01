package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * Primera adopción real de {@code @Version} (optimistic locking) en el
 * proyecto — corrige la condición de carrera detectada en
 * {@code IntentoExamenEntity} (sin @Version, sin constraint que la prevenga;
 * ver arquitectura aprobada §1.5 hallazgo #6). La UNIQUE de abajo evita
 * además que el mismo (actividad, asignación, estudiante, número de intento)
 * se duplique si dos requests concurrentes intentan iniciar a la vez.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity_attempt",
        uniqueConstraints = @UniqueConstraint(columnNames = {"activity_id", "assignment_id", "estudiante_id", "attempt_number"}))
public class ActivityAttemptEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    /** Trazabilidad de qué ActivityVersion vio el estudiante; no es la FK operacional (ver activityId). */
    @Column(name = "activity_version_id")
    private Long activityVersionId;

    /** NULL = práctica libre sin asignación formal. */
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EstadoActivityAttempt estado;

    @Column(name = "score", precision = 6, scale = 2)
    private BigDecimal score;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "json")
    private Map<String, Object> metadata;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;
}
