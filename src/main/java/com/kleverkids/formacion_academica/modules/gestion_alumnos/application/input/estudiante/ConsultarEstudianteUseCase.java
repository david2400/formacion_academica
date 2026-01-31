package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;

import java.util.UUID;

public interface ConsultarEstudianteUseCase {

    EstudianteDto consultarPorId(UUID estudianteId);
}
