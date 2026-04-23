package com.kleverkids.formacion_academica.modules.control_academico.application.input.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.ActualizarAsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.AsignacionExamenDto;

public interface ActualizarAsignacionExamenUseCase {
    AsignacionExamenDto actualizar(Long id, ActualizarAsignacionExamenDto dto);
}
