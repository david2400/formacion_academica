package com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;

import java.util.List;


public interface ListarObservacionesPorEstudianteUseCase {

    List<ObservacionCriterioDto> listar(Long examenId, Long estudianteId);
}
