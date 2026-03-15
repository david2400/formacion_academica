package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.time.Instant;


import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;

public record ExamSubmissionResponse(
    Long id,
    Long examId,
    Long studentId,
    Instant startedAt,
    Instant submittedAt,
    String status
) {
    public static ExamSubmissionResponse fromDomain(ExamSubmission submission) {
        return new ExamSubmissionResponse(
            submission.getId(),
            submission.getExamId(),
            submission.getStudentId(),
            submission.getStartedAt(),
            submission.getSubmittedAt(),
            submission.getStatus().name()
        );
    }
}
