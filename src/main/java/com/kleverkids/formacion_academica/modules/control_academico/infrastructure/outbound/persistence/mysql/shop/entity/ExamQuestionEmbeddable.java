package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class ExamQuestionEmbeddable {
    
    private UUID id;
    private UUID questionId;
    private int order;
    private BigDecimal points;
    private boolean required;
    
    public ExamQuestionEmbeddable() {}
    
    public ExamQuestionEmbeddable(UUID id, UUID questionId, int order, BigDecimal points, boolean required) {
        this.id = id;
        this.questionId = questionId;
        this.order = order;
        this.points = points;
        this.required = required;
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
