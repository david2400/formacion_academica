package com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_assignment;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

/**
 * Se publica cuando el progreso de un estudiante en una secuencia alcanza el
 * 100% (ver {@code StudentProgressService}). No es un evento de un agregado
 * persistido (student_sequence_progress es una proyección explícitamente "no
 * fuente de verdad" — arquitectura aprobada §2.3), así que se emite
 * directamente desde el Service, no acumulado vía AggregateRoot — desviación
 * pragmática documentada respecto a la intención general de §2.6.
 */
public class SequenceCompletedEvent extends DomainEvent {

    private final Long estudianteId;
    private final Long sequenceId;
    private final Long assignmentId;

    public SequenceCompletedEvent(Long estudianteId, Long sequenceId, Long assignmentId) {
        super();
        this.estudianteId = estudianteId;
        this.sequenceId = sequenceId;
        this.assignmentId = assignmentId;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    @Override
    public String getEventType() {
        return "SequenceCompleted";
    }
}
