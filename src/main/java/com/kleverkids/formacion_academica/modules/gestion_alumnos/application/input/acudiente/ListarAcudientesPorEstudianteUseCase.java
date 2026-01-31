package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;

import java.util.List;
import java.util.UUID;

public interface ListarAcudientesPorEstudianteUseCase {

    List<AcudienteDto> listar(UUID estudianteId);
}
