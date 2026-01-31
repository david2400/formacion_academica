package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record GradeExamCommand(
    UUID gradedBy,
    List<QuestionGradeDto> grades
) {
    public record QuestionGradeDto(
        UUID questionId,
        BigDecimal score,
        String feedback
    ) {}
}
