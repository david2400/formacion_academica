package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;

public interface ConsultarAulaUseCase {

    AulaDto consultarPorId(Long aulaId);
}
