package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.List;

public record RubricDto(
    List<CriterionDto> criteria
) {
    public record CriterionDto(
        String name,
        String description,
        int maxScore,
        List<LevelDto> levels
    ) {}
    
    public record LevelDto(
        String name,
        String description,
        int score
    ) {}
}
