package com.kleverkids.formacion_academica.modules.control_academico.application.input.estudiante_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;

import java.util.List;
import java.util.UUID;

public interface ListarEstudiantesExamenUseCase {

    List<EstudianteExamenDto> listar(UUID examenId);
}
