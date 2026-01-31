package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.util.List;

public record UpdateExamCommand(
    String name,
    String code,
    String subject,
    String gradeLevel,
    String instructions,
    TimeConfigDto timeConfig,
    List<ExamQuestionDto> questions,
    List<EvaluationCriteriaDto> criteria
) {}
