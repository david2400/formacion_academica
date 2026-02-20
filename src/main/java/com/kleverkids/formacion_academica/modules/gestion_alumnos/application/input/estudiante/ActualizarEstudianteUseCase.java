package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;

public interface ActualizarEstudianteUseCase {

    EstudianteDto actualizar(UpdateEstudianteDto request);
}
