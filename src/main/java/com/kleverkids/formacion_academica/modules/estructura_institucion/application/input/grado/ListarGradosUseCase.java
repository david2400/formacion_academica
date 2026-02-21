package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;

import java.util.List;

public interface ListarGradosUseCase {

    List<GradoDto> listar();
}
