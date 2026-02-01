package com.kleverkids.formacion_academica.modules.questions.domain.model;

import com.kleverkids.formacion_academica.modules.questions.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Difficulty;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Media;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.OrderingItem;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderingQuestion extends Question {
    
    private List<OrderingItem> items;
    private boolean partialCredit;
    
    public OrderingQuestion() {
        super();
        this.questionType = QuestionType.ORDERING;
    }
    
    public OrderingQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                             UUID themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             List<OrderingItem> items, boolean partialCredit) {
        super(id, questionText, QuestionType.ORDERING, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.items = items;
        this.partialCredit = partialCredit;
        validate();
    }
    
    public List<OrderingItem> getItems() {
        return items;
    }
    
    public void setItems(List<OrderingItem> items) {
        this.items = items;
    }
    
    public boolean isPartialCredit() {
        return partialCredit;
    }
    
    public void setPartialCredit(boolean partialCredit) {
        this.partialCredit = partialCredit;
    }
    
    @Override
    public void validate() {
        if (items == null || items.size() < 2) {
            throw new InvalidQuestionException("Ordering question must have at least 2 items");
        }
    }
}
