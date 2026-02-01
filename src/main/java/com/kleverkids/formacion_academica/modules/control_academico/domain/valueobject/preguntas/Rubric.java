package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.List;
import java.util.Objects;

public final class Rubric extends ValueObject {
    
    private final List<RubricCriterion> criteria;
    
    private Rubric(List<RubricCriterion> criteria) {
        this.criteria = criteria != null ? List.copyOf(criteria) : List.of();
    }
    
    public static Rubric create(List<RubricCriterion> criteria) {
        return new Rubric(criteria);
    }
    
    public List<RubricCriterion> getCriteria() {
        return criteria;
    }
    
    public int getTotalMaxScore() {
        return criteria.stream()
            .mapToInt(RubricCriterion::maxScore)
            .sum();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rubric rubric = (Rubric) o;
        return Objects.equals(criteria, rubric.criteria);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(criteria);
    }
    
    public record RubricCriterion(
        String name,
        String description,
        int maxScore,
        List<RubricLevel> levels
    ) {}
    
    public record RubricLevel(
        String name,
        String description,
        int score
    ) {}
}
