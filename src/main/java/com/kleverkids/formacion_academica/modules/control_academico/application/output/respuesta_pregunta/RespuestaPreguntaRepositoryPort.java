package com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;

import java.util.List;
import java.util.UUID;

public interface RespuestaPreguntaRepositoryPort {

    RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaPersistenceDto request);

    RespuestaPreguntaDto actualizar(ActualizarRespuestaPreguntaDto request);

    List<RespuestaPreguntaDto> listarPorEstudiante(UUID examenId, UUID estudianteId);
}
