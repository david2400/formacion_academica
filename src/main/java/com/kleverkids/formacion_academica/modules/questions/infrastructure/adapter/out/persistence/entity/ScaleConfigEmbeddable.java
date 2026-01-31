package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import java.util.List;

public class ScaleConfigEmbeddable {
    
    private int minValue;
    private int maxValue;
    private String minLabel;
    private String maxLabel;
    private List<String> labels;
    
    public ScaleConfigEmbeddable() {}
    
    public ScaleConfigEmbeddable(int minValue, int maxValue, String minLabel, String maxLabel, List<String> labels) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
        this.labels = labels;
    }
    
    public int getMinValue() { return minValue; }
    public void setMinValue(int minValue) { this.minValue = minValue; }
    
    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }
    
    public String getMinLabel() { return minLabel; }
    public void setMinLabel(String minLabel) { this.minLabel = minLabel; }
    
    public String getMaxLabel() { return maxLabel; }
    public void setMaxLabel(String maxLabel) { this.maxLabel = maxLabel; }
    
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
}
