package com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

import java.util.UUID;

public class QuestionUpdatedEvent extends DomainEvent {
    
    private final UUID questionId;
    
    public QuestionUpdatedEvent(UUID questionId) {
        super();
        this.questionId = questionId;
    }
    
    public UUID getQuestionId() {
        return questionId;
    }
    
    @Override
    public String getEventType() {
        return "question.updated";
    }
}
