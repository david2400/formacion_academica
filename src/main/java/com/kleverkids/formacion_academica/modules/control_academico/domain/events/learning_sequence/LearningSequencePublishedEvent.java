package com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_sequence;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

/**
 * Se publica cuando una LearningSequence pasa de DRAFT a PUBLISHED. Simétrico
 * a {@code ActivityPublishedEvent} (Etapa 1) — misma justificación: ambos
 * agregados adoptan AggregateRoot y publican por el mismo canal síncrono
 * in-process (ApplicationEventPublisher de Spring).
 */
public class LearningSequencePublishedEvent extends DomainEvent {

    private final Long sequenceId;
    private final Long versionId;

    public LearningSequencePublishedEvent(Long sequenceId, Long versionId) {
        super();
        this.sequenceId = sequenceId;
        this.versionId = versionId;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public Long getVersionId() {
        return versionId;
    }

    @Override
    public String getEventType() {
        return "LearningSequencePublished";
    }
}
