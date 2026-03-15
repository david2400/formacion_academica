package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;

import java.util.List;

public interface ListarPorAcudienteUseCase {

    List<EstudianteAcudienteDto> listarPorAcudiente(Long acudienteId);
}
