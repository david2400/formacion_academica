package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class EvaluationCriteriaEmbeddable {
    
    private UUID id;
    private String name;
    private String description;
    private BigDecimal weight;
    private BigDecimal maxScore;
    
    public EvaluationCriteriaEmbeddable() {}
    
    public EvaluationCriteriaEmbeddable(UUID id, String name, String description, BigDecimal weight, BigDecimal maxScore) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.maxScore = maxScore;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    
    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
}
