package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;

public interface ActualizarTematicaExamenUseCase {

    TematicaExamenDto actualizar(ActualizarTematicaExamenDto request);
}
