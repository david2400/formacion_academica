package com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.ActualizarRespuestaCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RegistrarRespuestaCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RespuestaCriterioDto;

import java.util.List;


public interface RespuestaCriterioRepositoryPort {

    RespuestaCriterioDto registrar(RegistrarRespuestaCriterioDto request);

    RespuestaCriterioDto actualizar(ActualizarRespuestaCriterioDto request);

    List<RespuestaCriterioDto> listarPorEstudiante(Long examenId, Long estudianteId);
}
