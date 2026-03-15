package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResponse;

public interface ConsultarExamenUseCase {
    
    ExamResponse consultarPorId(Long id);
}
