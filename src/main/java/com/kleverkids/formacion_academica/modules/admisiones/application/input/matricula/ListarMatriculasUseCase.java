package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;

import java.util.List;

public interface ListarMatriculasUseCase {

    List<Matricula> listarPorEstudiante(Long estudianteId);
}
