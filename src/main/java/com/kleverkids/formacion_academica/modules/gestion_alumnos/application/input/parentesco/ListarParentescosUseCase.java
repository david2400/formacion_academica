package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;

import java.util.List;

public interface ListarParentescosUseCase {
    List<Parentesco> listarTodos();
    List<Parentesco> listarActivos();
}
