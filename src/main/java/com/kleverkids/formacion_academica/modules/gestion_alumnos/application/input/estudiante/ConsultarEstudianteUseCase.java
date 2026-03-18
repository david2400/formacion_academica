package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;

public interface ConsultarEstudianteUseCase {

    Estudiante consultarPorId(Long estudianteId);
}
