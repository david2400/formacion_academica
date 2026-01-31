package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResponse;

import java.util.UUID;

public interface GetExamUseCase {
    
    ExamResponse getById(UUID id);
}
