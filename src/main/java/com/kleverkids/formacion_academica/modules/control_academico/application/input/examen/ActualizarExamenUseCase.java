package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.UpdateExamCommand;

public interface ActualizarExamenUseCase {
    
    ExamResponse actualizar(Long id, UpdateExamCommand command);
}
