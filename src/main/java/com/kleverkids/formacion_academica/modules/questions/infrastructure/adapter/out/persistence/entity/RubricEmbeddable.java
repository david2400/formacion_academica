package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import java.util.List;

public class RubricEmbeddable {
    
    private List<CriterionEmbeddable> criteria;
    
    public RubricEmbeddable() {}
    
    public RubricEmbeddable(List<CriterionEmbeddable> criteria) {
        this.criteria = criteria;
    }
    
    public List<CriterionEmbeddable> getCriteria() { return criteria; }
    public void setCriteria(List<CriterionEmbeddable> criteria) { this.criteria = criteria; }
    
    public static class CriterionEmbeddable {
        private String name;
        private String description;
        private int maxScore;
        private List<LevelEmbeddable> levels;
        
        public CriterionEmbeddable() {}
        
        public CriterionEmbeddable(String name, String description, int maxScore, List<LevelEmbeddable> levels) {
            this.name = name;
            this.description = description;
            this.maxScore = maxScore;
            this.levels = levels;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public int getMaxScore() { return maxScore; }
        public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
        
        public List<LevelEmbeddable> getLevels() { return levels; }
        public void setLevels(List<LevelEmbeddable> levels) { this.levels = levels; }
    }
    
    public static class LevelEmbeddable {
        private String name;
        private String description;
        private int score;
        
        public LevelEmbeddable() {}
        
        public LevelEmbeddable(String name, String description, int score) {
            this.name = name;
            this.description = description;
            this.score = score;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
    }
}
