package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSubmissionResponse;

import java.util.UUID;

public interface IniciarExamenUseCase {
    
    ExamSubmissionResponse iniciar(UUID examenId, UUID estudianteId);
}
