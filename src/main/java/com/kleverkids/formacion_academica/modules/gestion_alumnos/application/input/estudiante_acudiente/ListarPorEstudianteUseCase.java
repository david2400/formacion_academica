package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;

import java.util.List;

public interface ListarPorEstudianteUseCase {

    List<EstudianteAcudiente> listarPorEstudiante(Long estudianteId);
}
