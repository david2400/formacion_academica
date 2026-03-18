package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;

import java.util.List;

public interface ListarAulasUseCase {

    List<Aula> listar();
}
