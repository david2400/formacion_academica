package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.util.List;


public record SubmitExamCommand(
    Long submissionId,
    Long studentId,
    List<QuestionAnswerDto> answers
) {}
