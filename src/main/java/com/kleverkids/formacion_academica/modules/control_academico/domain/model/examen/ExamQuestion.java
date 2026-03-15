package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.math.BigDecimal;


public class ExamQuestion {
    
    private Long id;
    private Long questionId;
    private int order;
    private BigDecimal points;
    private boolean required;
    
    public ExamQuestion() {}
    
    public ExamQuestion(Long id, Long questionId, int order, BigDecimal points, boolean required) {
        this.id = id ;
        this.questionId = questionId;
        this.order = order;
        this.points = points != null ? points : BigDecimal.ONE;
        this.required = required;
    }
    
    public static ExamQuestion create(Long questionId, int order, BigDecimal points, boolean required) {
        return new ExamQuestion(null, questionId, order, points, required);
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
