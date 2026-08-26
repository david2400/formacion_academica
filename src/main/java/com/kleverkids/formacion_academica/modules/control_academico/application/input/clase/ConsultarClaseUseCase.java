package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;

import java.util.Optional;

public interface ConsultarClaseUseCase {

    Optional<Clase> consultarPorId(Long id);
}
