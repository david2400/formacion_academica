package com.kleverkids.formacion_academica.modules.control_academico.application.input.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.AsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.CrearAsignacionExamenDto;

public interface CrearAsignacionExamenUseCase {
    AsignacionExamenDto crear(CrearAsignacionExamenDto dto);
}
