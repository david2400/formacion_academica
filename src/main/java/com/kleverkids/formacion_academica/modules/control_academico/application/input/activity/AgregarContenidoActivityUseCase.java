package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;

public interface AgregarContenidoActivityUseCase {

    ActivityContentItem agregarContenido(Long activityId, AgregarContenidoActivityDto request);
}
