package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;

import java.util.List;

public interface ListarNivelesEducativosUseCase {

    List<NivelEducativo> listarTodos();
    List<NivelEducativo> listarPorNivelSuperior(Long nivelSuperiorId);
    List<NivelEducativo> listarActivos();
    List<NivelEducativo> listarNivelesPrincipales();
}
