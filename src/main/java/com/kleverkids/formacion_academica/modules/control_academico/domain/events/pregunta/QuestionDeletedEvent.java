package com.kleverkids.formacion_academica.modules.questions.domain.event;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

import java.util.UUID;

public class QuestionDeletedEvent extends DomainEvent {
    
    private final UUID questionId;
    
    public QuestionDeletedEvent(UUID questionId) {
        super();
        this.questionId = questionId;
    }
    
    public UUID getQuestionId() {
        return questionId;
    }
    
    @Override
    public String getEventType() {
        return "question.deleted";
    }
}
