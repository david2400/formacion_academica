package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("numeric")
public class NumericQuestionEntity extends QuestionEntity {
    
    @Column(name = "correct_value", precision = 19, scale = 6)
    private BigDecimal correctValue;
    
    @Column(name = "tolerance", precision = 19, scale = 6)
    private BigDecimal tolerance;
    
    @Column(name = "unit")
    private String unit;
    
    @Column(name = "decimal_places")
    private Integer decimalPlaces;
    
    public BigDecimal getCorrectValue() { return correctValue; }
    public void setCorrectValue(BigDecimal correctValue) { this.correctValue = correctValue; }
    
    public BigDecimal getTolerance() { return tolerance; }
    public void setTolerance(BigDecimal tolerance) { this.tolerance = tolerance; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public Integer getDecimalPlaces() { return decimalPlaces; }
    public void setDecimalPlaces(Integer decimalPlaces) { this.decimalPlaces = decimalPlaces; }
}
