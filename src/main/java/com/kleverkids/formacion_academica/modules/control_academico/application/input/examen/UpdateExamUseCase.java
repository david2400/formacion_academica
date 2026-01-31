package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.UpdateExamCommand;

import java.util.UUID;

public interface UpdateExamUseCase {
    
    ExamResponse update(UUID id, UpdateExamCommand command);
}
