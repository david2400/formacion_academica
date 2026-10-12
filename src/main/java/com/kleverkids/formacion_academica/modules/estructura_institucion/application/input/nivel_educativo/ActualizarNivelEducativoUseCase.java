package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;

import java.util.Optional;

public interface ActualizarNivelEducativoUseCase {

    Optional<NivelEducativo> actualizar(Long id, ActualizarNivelEducativoDto dto);
}
