package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubmissionResponse {
    private Long id;
    private Long examId;
    private Long studentId;
    private Instant startedAt;
    private Instant submittedAt;
    private String status;

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
