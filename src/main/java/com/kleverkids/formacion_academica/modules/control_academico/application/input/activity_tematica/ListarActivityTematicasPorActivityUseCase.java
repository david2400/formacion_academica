package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;

import java.util.List;

public interface ListarActivityTematicasPorActivityUseCase {

    List<ActivityTematicaDto> listarPorActivity(Long activityId);
}
