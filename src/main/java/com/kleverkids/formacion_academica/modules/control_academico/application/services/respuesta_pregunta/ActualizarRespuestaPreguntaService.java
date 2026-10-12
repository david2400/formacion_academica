package com.kleverkids.formacion_academica.modules.control_academico.application.services.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ActualizarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarRespuestaPreguntaService implements ActualizarRespuestaPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;

    @Override
    public RespuestaPreguntaDto actualizar(ActualizarRespuestaPreguntaDto request) {
        return respuestaRepository.actualizar(request);
    }
}
