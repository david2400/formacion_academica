package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;

import java.util.Optional;

public interface ConsultarParentescoUseCase {
    Optional<Parentesco> consultarPorId(Long parentescoId);
}
