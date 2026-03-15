package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;

import java.util.List;

public interface ObtenerResultadosExamenUseCase {
    
    List<ExamResultResponse> obtenerResultados(Long examenId);
    
    ExamResultResponse obtenerResultadoEstudiante(Long examenId, Long estudianteId);
}
