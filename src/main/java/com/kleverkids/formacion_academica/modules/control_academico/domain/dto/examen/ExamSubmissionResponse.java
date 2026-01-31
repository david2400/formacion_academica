package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamSubmission;

import java.time.Instant;
import java.util.UUID;

public record ExamSubmissionResponse(
    UUID id,
    UUID examId,
    UUID studentId,
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
