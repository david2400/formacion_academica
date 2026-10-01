package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity_attempt.ActivityAttemptCompletedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity_attempt.ActivityAttemptStartedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Agregado raíz de un intento de un estudiante sobre una {@code Activity}.
 * Tercera clase de dominio del proyecto en adoptar {@link AggregateRoot} de
 * verdad (después de {@code Activity} y {@code LearningSequence}).
 *
 * <p>Corrección respecto al documento original: además de
 * {@code activityVersionId} (trazabilidad de qué versión publicada vio el
 * estudiante), se agrega {@code activityId} como FK operacional directa —
 * mismo criterio ya aplicado en Etapa 2 a {@code LearningSequenceItem}
 * (operar siempre sobre la entidad viva/estable, no sobre la versión
 * congelada, que es solo para trazabilidad).
 *
 * <p>Primera adopción real de {@code @Version} (optimistic locking) del
 * proyecto, en la entidad JPA correspondiente — corrige la condición de
 * carrera detectada en {@code IntentoExamen} (ver arquitectura aprobada,
 * hallazgo #6).
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActivityAttempt extends AggregateRoot<Long> {

    private Long id;
    private Long activityId;

    /** FK a ActivityVersion.id vigente al iniciar el intento (trazabilidad; nullable si la actividad no tenía versión publicada aún — no debería ocurrir en la práctica, ver validación en el Service). */
    private Long activityVersionId;

    /** NULL = práctica libre sin asignación formal. */
    private Long assignmentId;

    private Long estudianteId;
    private Integer attemptNumber;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private EstadoActivityAttempt estado;
    private BigDecimal score;

    /** JSON acotado: metadata operativa del intento (tiempo por paso, etc.), no contenido. */
    private Map<String, Object> metadata;

    /** Contador de optimistic locking (@Version en la entidad JPA). */
    private Integer version;

    @Override
    public Long getId() {
        return id;
    }

    public void registrarEventoInicio() {
        registerEvent(new ActivityAttemptStartedEvent(this.id, this.activityId, this.estudianteId, this.assignmentId));
    }

    public void registrarEventoCompletado() {
        registerEvent(new ActivityAttemptCompletedEvent(this.id, this.activityId, this.estudianteId, this.assignmentId, this.score));
    }
}
