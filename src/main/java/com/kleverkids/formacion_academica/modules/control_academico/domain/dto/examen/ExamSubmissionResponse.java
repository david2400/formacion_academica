package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.time.Instant;


import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;

public record ExamSubmissionResponse(
    Long id,
    Long examId,
    Long studentId,
    Instant startedAt,
    Instant submittedAt,
    String status
) {
    public static ExamSubmissionResponse fromDomain(EnvioExamen submission) {
        return new ExamSubmissionResponse(
            submission.getId(),
            submission.getExamenId(),
            submission.getEstudianteId(),
            submission.getIniciadoEn(),
            submission.getIniciadoEn(),
            submission.getEstado().name()
        );
    }
}
