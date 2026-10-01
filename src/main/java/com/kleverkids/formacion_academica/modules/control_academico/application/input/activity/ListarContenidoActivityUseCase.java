package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;

import java.util.List;

public interface ListarContenidoActivityUseCase {

    List<ActivityContentItem> listarContenido(Long activityId);
}
