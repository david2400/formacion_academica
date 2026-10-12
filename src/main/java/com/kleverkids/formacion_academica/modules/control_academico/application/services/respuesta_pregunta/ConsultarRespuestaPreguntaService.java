package com.kleverkids.formacion_academica.modules.control_academico.application.services.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ConsultarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarRespuestaPreguntaService implements ConsultarRespuestaPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;

    @Override
    public RespuestaPreguntaDto consultarPorId(Long respuestaId) {
        return respuestaRepository.obtenerPorId(respuestaId);
    }
}
