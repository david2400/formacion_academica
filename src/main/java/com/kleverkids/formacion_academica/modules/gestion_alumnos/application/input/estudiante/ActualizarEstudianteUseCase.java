package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.ActualizarEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;

public interface ActualizarEstudianteUseCase {

    EstudianteDto actualizar(ActualizarEstudianteDto request);
}
