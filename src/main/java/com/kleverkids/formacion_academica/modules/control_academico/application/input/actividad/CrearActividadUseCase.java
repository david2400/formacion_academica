package com.kleverkids.formacion_academica.modules.control_academico.application.input.actividad;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;

public interface CrearActividadUseCase {
    
    Actividad crear(CrearActividadDto request);
}
