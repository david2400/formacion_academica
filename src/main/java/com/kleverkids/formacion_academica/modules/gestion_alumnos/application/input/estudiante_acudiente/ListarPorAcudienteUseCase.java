package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;

import java.util.List;
import java.util.UUID;

public interface ListarPorAcudienteUseCase {

    List<EstudianteAcudienteDto> listarPorAcudiente(UUID acudienteId);
}
