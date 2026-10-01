package com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

/**
 * Se publica cuando una {@code Activity} pasa de DRAFT a PUBLISHED, es decir,
 * cuando queda congelada una {@code ActivityVersion} nueva. Replica el patrón
 * de publicación existente (puerto + adapter -> ApplicationEventPublisher de
 * Spring, eventos síncronos in-process, sin mensajería externa) ya usado por
 * el módulo de preguntas.
 */
public class ActivityPublishedEvent extends DomainEvent {

    private final Long activityId;
    private final Long activityTypeId;
    private final Long versionId;

    public ActivityPublishedEvent(Long activityId, Long activityTypeId, Long versionId) {
        super();
        this.activityId = activityId;
        this.activityTypeId = activityTypeId;
        this.versionId = versionId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public Long getVersionId() {
        return versionId;
    }

    @Override
    public String getEventType() {
        return "ActivityPublished";
    }
}
