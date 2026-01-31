package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.GradeExamCommand;

import java.util.UUID;

public interface GradeExamUseCase {
    
    ExamResultResponse grade(UUID examId, UUID submissionId, GradeExamCommand command);
}
