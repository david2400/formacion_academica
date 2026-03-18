package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.tematica.Tematica;

public interface ConsultarTematicaUseCase {

    Tematica consultarPorId(Long tematicaId);
}
