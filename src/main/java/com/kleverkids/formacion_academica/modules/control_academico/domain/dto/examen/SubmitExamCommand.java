package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.util.List;
import java.util.UUID;

public record SubmitExamCommand(
    UUID submissionId,
    UUID studentId,
    List<QuestionAnswerDto> answers
) {}
