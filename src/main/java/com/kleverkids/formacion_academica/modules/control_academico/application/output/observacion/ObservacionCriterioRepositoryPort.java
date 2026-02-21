package com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;

import java.util.List;
import java.util.UUID;

public interface ObservacionCriterioRepositoryPort {

    ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request);

    ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request);

    List<ObservacionCriterioDto> listarPorEstudiante(UUID examenId, UUID estudianteId);

    ObservacionCriterioDto obtenerPorId(UUID observacionId);

    void eliminar(UUID observacionId);
}
