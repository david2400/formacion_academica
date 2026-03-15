package com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;



public class QuestionUpdatedEvent extends DomainEvent {
    
    private final Long questionId;
    
    public QuestionUpdatedEvent(Long questionId) {
        super();
        this.questionId = questionId;
    }
    
    public Long getQuestionId() {
        return questionId;
    }
    
    @Override
    public String getEventType() {
        return "question.updated";
    }
}
