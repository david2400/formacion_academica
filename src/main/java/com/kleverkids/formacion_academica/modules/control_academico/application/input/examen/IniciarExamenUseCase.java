package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSubmissionResponse;

public interface IniciarExamenUseCase {
    
    ExamSubmissionResponse iniciar(Long examenId, Long estudianteId);
}
