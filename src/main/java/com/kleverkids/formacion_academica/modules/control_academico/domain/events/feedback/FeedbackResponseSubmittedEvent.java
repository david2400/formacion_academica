package com.kleverkids.formacion_academica.modules.control_academico.domain.events.feedback;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

/**
 * Se recibió un feedback. Punto de enganche para integraciones futuras
 * (alertas por bajo desempeño, IA sobre comentarios, BI) sin tocar el núcleo.
 *
 * <p>No lleva estudiante ni id de respuesta a propósito: un listener que los
 * registrara en un log rompería el anonimato.
 */
public class FeedbackResponseSubmittedEvent extends DomainEvent {

    private final Long assignmentId;
    private final Long formId;
    private final Long claseId;

    public FeedbackResponseSubmittedEvent(Long assignmentId, Long formId, Long claseId) {
        super();
        this.assignmentId = assignmentId;
        this.formId = formId;
        this.claseId = claseId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getFormId() {
        return formId;
    }

    public Long getClaseId() {
        return claseId;
    }

    @Override
    public String getEventType() {
        return "FEEDBACK_RESPONSE_SUBMITTED";
    }
}
