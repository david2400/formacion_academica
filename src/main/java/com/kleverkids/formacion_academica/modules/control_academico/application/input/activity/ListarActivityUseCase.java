package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

import java.util.List;

public interface ListarActivityUseCase {

    List<Activity> listarTodas();

    List<Activity> buscar(FiltroActivityDto filtro);
}
