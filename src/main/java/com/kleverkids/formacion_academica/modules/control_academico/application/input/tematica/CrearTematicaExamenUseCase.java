package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;

public interface CrearTematicaExamenUseCase {

    TematicaExamenDto crear(CrearTematicaExamenDto request);
}
