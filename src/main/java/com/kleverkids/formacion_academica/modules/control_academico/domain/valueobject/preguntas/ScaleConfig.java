package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.List;
import java.util.Objects;

public final class ScaleConfig extends ValueObject {
    
    private final int minValue;
    private final int maxValue;
    private final String minLabel;
    private final String maxLabel;
    private final List<String> labels;
    
    private ScaleConfig(int minValue, int maxValue, String minLabel, String maxLabel, List<String> labels) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("Min value must be less than max value");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
        this.labels = labels != null ? List.copyOf(labels) : List.of();
    }
    
    public static ScaleConfig create(int minValue, int maxValue, String minLabel, String maxLabel, List<String> labels) {
        return new ScaleConfig(minValue, maxValue, minLabel, maxLabel, labels);
    }
    
    public static ScaleConfig likert5() {
        return new ScaleConfig(1, 5, "Strongly Disagree", "Strongly Agree", 
            List.of("Strongly Disagree", "Disagree", "Neutral", "Agree", "Strongly Agree"));
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public String getMinLabel() {
        return minLabel;
    }
    
    public String getMaxLabel() {
        return maxLabel;
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaleConfig that = (ScaleConfig) o;
        return minValue == that.minValue && maxValue == that.maxValue;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(minValue, maxValue);
    }
}
