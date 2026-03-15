package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes;

import java.math.BigDecimal;


public class ExamQuestionEmbeddable {
    
    private Long id;
    private Long questionId;
    private int order;
    private BigDecimal points;
    private boolean required;
    
    public ExamQuestionEmbeddable() {}
    
    public ExamQuestionEmbeddable(Long id, Long questionId, int order, BigDecimal points, boolean required) {
        this.id = id;
        this.questionId = questionId;
        this.order = order;
        this.points = points;
        this.required = required;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    
    public BigDecimal getPoints() { return points; }
    public void setPoints(BigDecimal points) { this.points = points; }
    
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}
