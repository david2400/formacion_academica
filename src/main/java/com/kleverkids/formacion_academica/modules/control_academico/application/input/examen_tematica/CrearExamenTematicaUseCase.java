package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;

public interface CrearExamenTematicaUseCase {

    ExamenTematicaDto asignar(CrearExamenTematicaDto request);
}
