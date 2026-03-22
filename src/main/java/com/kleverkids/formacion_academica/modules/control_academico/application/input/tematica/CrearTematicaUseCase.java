package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;

public interface CrearTematicaUseCase {

    Tematica crear(CrearTematicaDto request);
}
