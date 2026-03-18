package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;

public interface ConsultarAulaUseCase {

    Aula consultarPorId(Long aulaId);
}
