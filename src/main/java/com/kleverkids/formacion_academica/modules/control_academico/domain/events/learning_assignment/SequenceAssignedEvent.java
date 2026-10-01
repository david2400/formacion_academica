package com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_assignment;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public class SequenceAssignedEvent extends DomainEvent {

    private final Long assignmentId;
    private final Long sequenceId;
    private final Long grupoId;
    private final Long estudianteId;

    public SequenceAssignedEvent(Long assignmentId, Long sequenceId, Long grupoId, Long estudianteId) {
        super();
        this.assignmentId = assignmentId;
        this.sequenceId = sequenceId;
        this.grupoId = grupoId;
        this.estudianteId = estudianteId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    @Override
    public String getEventType() {
        return "SequenceAssigned";
    }
}
