package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;

public interface CrearNivelEducativoUseCase {

    NivelEducativo crear(CrearNivelEducativoDto dto);
}
