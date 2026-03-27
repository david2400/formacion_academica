package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;

public interface ConsultarGrupoUseCase {

    Grupo consultarPorId(Long grupoId);
}
