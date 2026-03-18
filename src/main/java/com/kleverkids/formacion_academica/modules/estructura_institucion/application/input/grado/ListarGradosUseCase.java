package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;

import java.util.List;

public interface ListarGradosUseCase {

    List<Grado> listar();
}
