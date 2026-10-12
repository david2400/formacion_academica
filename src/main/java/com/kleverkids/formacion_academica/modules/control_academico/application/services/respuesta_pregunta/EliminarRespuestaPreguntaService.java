package com.kleverkids.formacion_academica.modules.control_academico.application.services.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.EliminarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarRespuestaPreguntaService implements EliminarRespuestaPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;

    @Override
    public void eliminar(Long respuestaId) {
        respuestaRepository.obtenerPorId(respuestaId);
        respuestaRepository.eliminar(respuestaId);
    }
}
