package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

public interface ActualizarActivityUseCase {

    Activity actualizar(ActualizarActivityDto request);
}
