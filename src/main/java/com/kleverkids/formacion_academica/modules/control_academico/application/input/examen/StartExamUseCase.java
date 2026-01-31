package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSubmissionResponse;

import java.util.UUID;

public interface StartExamUseCase {
    
    ExamSubmissionResponse start(UUID examId, UUID studentId);
}
