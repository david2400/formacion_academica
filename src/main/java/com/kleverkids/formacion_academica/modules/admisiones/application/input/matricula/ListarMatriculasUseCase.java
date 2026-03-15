package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;

import java.util.List;

public interface ListarMatriculasUseCase {

    List<MatriculaDto> listarPorEstudiante(Long estudianteId);
}
