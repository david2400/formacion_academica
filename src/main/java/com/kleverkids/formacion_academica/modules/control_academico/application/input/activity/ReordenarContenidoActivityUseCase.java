package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;

import java.util.List;

public interface ReordenarContenidoActivityUseCase {

    List<ActivityContentItem> reordenar(Long activityId, ReordenarContenidoActivityDto request);
}
