package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;

import java.util.List;

public interface ListarGruposUseCase {

    List<Grupo> listar();
}
