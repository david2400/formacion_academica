package com.kleverkids.formacion_academica.modules.control_academico.application.input.estudiante_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;

import java.util.List;

public interface ListarEstudiantesExamenUseCase {

    List<EstudianteExamenDto> listar(Long examenId);
}
