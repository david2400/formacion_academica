package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.tematica.Tematica;

import java.util.List;

public interface ListarTematicasUseCase {

    List<Tematica> listar(Long examenId);
}
