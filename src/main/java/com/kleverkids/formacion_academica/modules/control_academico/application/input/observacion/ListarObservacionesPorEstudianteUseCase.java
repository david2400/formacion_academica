package com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;

import java.util.List;
import java.util.UUID;

public interface ListarObservacionesPorEstudianteUseCase {

    List<ObservacionCriterioDto> listar(UUID examenId, UUID estudianteId);
}
