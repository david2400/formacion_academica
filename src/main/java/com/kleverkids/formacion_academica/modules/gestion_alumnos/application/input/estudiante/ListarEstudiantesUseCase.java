package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;

import java.util.List;

public interface ListarEstudiantesUseCase {

    List<Estudiante> listar();
}
