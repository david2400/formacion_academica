package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;

public interface ActualizarTematicaUseCase {

    Tematica actualizar(ActualizarTematicaDto request);
}
