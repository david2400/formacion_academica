package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.GradeExamCommand;

public interface CalificarExamenUseCase {
    
    ExamResultResponse calificar(Long examenId, Long envioId, GradeExamCommand command);
}
