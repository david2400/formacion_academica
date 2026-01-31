package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ExamQuestion {
    
    private UUID id;
    private UUID questionId;
    private int order;
    private BigDecimal points;
    private boolean required;
    
    public ExamQuestion() {}
    
    public ExamQuestion(UUID id, UUID questionId, int order, BigDecimal points, boolean required) {
        this.id = id != null ? id : UUID.randomUUID();
        this.questionId = questionId;
        this.order = order;
        this.points = points != null ? points : BigDecimal.ONE;
        this.required = required;
    }
    
    public static ExamQuestion create(UUID questionId, int order, BigDecimal points, boolean required) {
        return new ExamQuestion(null, questionId, order, points, required);
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getQuestionId() { return questionId; }
    public void setQuestionId(UUID questionId) { this.questionId = questionId; }
    
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    
    public BigDecimal getPoints() { return points; }
    public void setPoints(BigDecimal points) { this.points = points; }
    
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}
