package com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity_attempt;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

import java.math.BigDecimal;

public class ActivityAttemptCompletedEvent extends DomainEvent {

    private final Long attemptId;
    private final Long activityId;
    private final Long estudianteId;
    private final Long assignmentId;
    private final BigDecimal score;

    public ActivityAttemptCompletedEvent(Long attemptId, Long activityId, Long estudianteId, Long assignmentId, BigDecimal score) {
        super();
        this.attemptId = attemptId;
        this.activityId = activityId;
        this.estudianteId = estudianteId;
        this.assignmentId = assignmentId;
        this.score = score;
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

    public BigDecimal getScore() {
        return score;
    }

    @Override
    public String getEventType() {
        return "ActivityAttemptCompleted";
    }
}
