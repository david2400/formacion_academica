package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;

public interface CrearEstudianteUseCase {

    EstudianteDto crear(CrearEstudianteDto request);
}
