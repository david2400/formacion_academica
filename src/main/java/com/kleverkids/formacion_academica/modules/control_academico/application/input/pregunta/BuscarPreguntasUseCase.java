package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CriterioBusquedaPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuscarPreguntasUseCase {
    
    Page<RespuestaPregunta> buscar(CriterioBusquedaPregunta criterios, Pageable pageable);
}
