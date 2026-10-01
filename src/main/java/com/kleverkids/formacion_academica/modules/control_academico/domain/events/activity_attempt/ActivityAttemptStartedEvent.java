package com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity_attempt;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public class ActivityAttemptStartedEvent extends DomainEvent {

    private final Long attemptId;
    private final Long activityId;
    private final Long estudianteId;
    private final Long assignmentId;

    public ActivityAttemptStartedEvent(Long attemptId, Long activityId, Long estudianteId, Long assignmentId) {
        super();
        this.attemptId = attemptId;
        this.activityId = activityId;
        this.estudianteId = estudianteId;
        this.assignmentId = assignmentId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    @Override
    public String getEventType() {
        return "ActivityAttemptStarted";
    }
}
