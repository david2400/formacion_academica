package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;

import java.util.List;

public interface ListarAulasUseCase {

    List<AulaDto> listar();
}
