package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;

public interface CrearAulaUseCase {

    AulaDto crear(CrearAulaDto request);
}
