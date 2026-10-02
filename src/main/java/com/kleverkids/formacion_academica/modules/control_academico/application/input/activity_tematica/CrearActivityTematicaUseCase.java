package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;

public interface CrearActivityTematicaUseCase {

    ActivityTematicaDto asignar(CrearActivityTematicaDto request);
}
