package com.kleverkids.formacion_academica.modules.questions.domain.event;

import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.QuestionType;
import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

import java.util.UUID;

public class QuestionCreatedEvent extends DomainEvent {
    
    private final UUID questionId;
    private final QuestionType questionType;
    private final UUID themeId;
    
    public QuestionCreatedEvent(UUID questionId, QuestionType questionType, UUID themeId) {
        super();
        this.questionId = questionId;
        this.questionType = questionType;
        this.themeId = themeId;
    }
    
    public UUID getQuestionId() {
        return questionId;
    }
    
    public QuestionType getQuestionType() {
        return questionType;
    }
    
    public UUID getThemeId() {
        return themeId;
    }
    
    @Override
    public String getEventType() {
        return "question.created";
    }
}
