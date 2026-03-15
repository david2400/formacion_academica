package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;

public interface ConsultarEstudianteAcudienteUseCase {

    EstudianteAcudienteDto consultarPorId(Long relacionId);
}
