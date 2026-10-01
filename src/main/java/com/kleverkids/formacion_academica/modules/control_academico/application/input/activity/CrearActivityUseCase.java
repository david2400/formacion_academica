package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

public interface CrearActivityUseCase {

    Activity crear(CrearActivityDto request);
}
