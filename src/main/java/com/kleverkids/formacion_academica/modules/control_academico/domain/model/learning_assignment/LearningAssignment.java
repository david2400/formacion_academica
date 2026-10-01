package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_assignment.SequenceAssignedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Agregado raíz de la asignación de una {@code LearningSequence} a un grupo o
 * a un estudiante puntual (terreno nuevo, sin precedente directo en el
 * proyecto — ver arquitectura aprobada §1.5 hallazgo #8).
 *
 * <p>Corrección respecto al documento original: además de
 * {@code sequenceVersionId} (trazabilidad de qué versión publicada se asignó),
 * se agrega {@code sequenceId} como FK operacional directa — mismo criterio
 * ya aplicado en Etapa 2 a {@code LearningSequenceItem} (operar sobre la
 * secuencia viva, no sobre la versión congelada).
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LearningAssignment extends AggregateRoot<Long> {

    private Long id;
    private Long sequenceId;

    /** FK a LearningSequenceVersion.id vigente al momento de asignar (trazabilidad). */
    private Long sequenceVersionId;

    /** Exactamente uno de grupoId/estudianteId está informado (ver CHECK en la entidad JPA). */
    private Long grupoId;
    private Long estudianteId;

    private Long assignedBy;
    private LocalDateTime assignedAt;
    private LocalDateTime availableFrom;
    private LocalDateTime dueAt;
    private boolean allowLateSubmission;
    private boolean lockAfterDue;
    private EstadoLearningAssignment estado;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void registrarEventoAsignacion() {
        registerEvent(new SequenceAssignedEvent(this.id, this.sequenceId, this.grupoId, this.estudianteId));
    }
}
