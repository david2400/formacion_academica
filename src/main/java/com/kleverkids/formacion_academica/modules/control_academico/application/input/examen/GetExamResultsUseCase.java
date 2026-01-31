package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;

import java.util.List;
import java.util.UUID;

public interface GetExamResultsUseCase {
    
    List<ExamResultResponse> getResults(UUID examId);
    
    ExamResultResponse getStudentResult(UUID examId, UUID studentId);
}
