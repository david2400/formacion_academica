package com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;

import java.util.List;

public interface RespuestaPreguntaRepositoryPort {

    RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaPersistenceDto request);

    RespuestaPreguntaDto actualizar(ActualizarRespuestaPreguntaDto request);

    List<RespuestaPreguntaDto> listarPorEstudiante(Long examenId, Long estudianteId);

    RespuestaPreguntaDto obtenerPorId(Long respuestaId);

    void eliminar(Long respuestaId);
}
