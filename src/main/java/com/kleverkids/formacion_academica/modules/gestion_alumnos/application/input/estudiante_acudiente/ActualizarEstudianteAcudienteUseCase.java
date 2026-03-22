package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;

public interface ActualizarEstudianteAcudienteUseCase {

    EstudianteAcudiente actualizar(ActualizarEstudianteAcudienteDto request);
}
