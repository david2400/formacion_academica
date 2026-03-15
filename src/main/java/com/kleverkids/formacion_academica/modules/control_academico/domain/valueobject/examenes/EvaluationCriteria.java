package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;


public final class EvaluationCriteria extends ValueObject {
    
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal weight;
    private final BigDecimal maxScore;
    
    private EvaluationCriteria(Long id, String name, String description, BigDecimal weight, BigDecimal maxScore) {
        this.id = id ;
        this.name = Objects.requireNonNull(name, "El nombre del criterio no puede ser nulo");
        this.description = description;
        this.weight = weight != null ? weight : BigDecimal.ONE;
        this.maxScore = maxScore != null ? maxScore : BigDecimal.valueOf(100);
    }
    
    public static EvaluationCriteria create(String name, String description, BigDecimal weight, BigDecimal maxScore) {
        return new EvaluationCriteria(null, name, description, weight, maxScore);
    }
    
    public static EvaluationCriteria create(Long id, String name, String description, BigDecimal weight, BigDecimal maxScore) {
        return new EvaluationCriteria(id, name, description, weight, maxScore);
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public BigDecimal getMaxScore() {
        return maxScore;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationCriteria that = (EvaluationCriteria) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
