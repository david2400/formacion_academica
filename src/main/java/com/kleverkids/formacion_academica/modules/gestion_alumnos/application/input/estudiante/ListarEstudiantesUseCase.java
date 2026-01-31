package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;

import java.util.List;

public interface ListarEstudiantesUseCase {

    List<EstudianteDto> listar();
}
