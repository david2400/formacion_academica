package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

import java.util.Optional;

public interface ConsultarActivityUseCase {

    Optional<Activity> consultarPorId(Long id);
}
