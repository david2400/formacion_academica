package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;

import java.util.Optional;

public interface ConsultarNivelEducativoUseCase {

    Optional<NivelEducativo> buscarPorId(Long id);
    Optional<NivelEducativo> buscarPorCodigo(String codigo);
}
