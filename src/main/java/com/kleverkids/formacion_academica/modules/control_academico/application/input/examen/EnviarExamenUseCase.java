package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.SubmitExamCommand;

public interface EnviarExamenUseCase {
    
    ExamResultResponse enviar(Long examenId, SubmitExamCommand command);
}
