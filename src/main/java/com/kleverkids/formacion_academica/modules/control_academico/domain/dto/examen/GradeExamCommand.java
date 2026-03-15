package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.List;


public record GradeExamCommand(
    Long gradedBy,
    List<QuestionGradeDto> grades
) {
    public record QuestionGradeDto(
        Long questionId,
        BigDecimal score,
        String feedback
    ) {}
}
