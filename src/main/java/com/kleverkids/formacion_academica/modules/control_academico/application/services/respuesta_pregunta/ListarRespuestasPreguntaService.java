package com.kleverkids.formacion_academica.modules.control_academico.application.services.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ListarRespuestasPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarRespuestasPreguntaService implements ListarRespuestasPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;

    @Override
    public List<RespuestaPreguntaDto> listar(Long examenId, Long estudianteId) {
        return respuestaRepository.listarPorEstudiante(examenId, estudianteId);
    }
}
