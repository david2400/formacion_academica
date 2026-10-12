package com.kleverkids.formacion_academica.modules.control_academico.application.services.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.RegistrarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen.EstudianteExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrarRespuestaPreguntaService implements RegistrarRespuestaPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;
    private final EstudianteExamenRepositoryPort estudianteExamenRepository;

    @Override
    public RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaDto request) {
        Long examenId = request.examenId();
        Long estudianteId = request.estudianteId();
        EstudianteExamenDto relacion = estudianteExamenRepository.buscarPorExamenYEstudiante(examenId, estudianteId)
                .orElseGet(() -> estudianteExamenRepository.registrar(new RegistrarEstudianteExamenDto(examenId, estudianteId, LocalDateTime.now())));

        RegistrarRespuestaPreguntaPersistenceDto payload = new RegistrarRespuestaPreguntaPersistenceDto(
                relacion.getId(),
                examenId,
                estudianteId,
                request.preguntaId(),
                request.respuestaBancoId(),
                request.respuestaTexto(),
                request.esCorrecta(),
                request.puntajeObtenido()
        );
        return respuestaRepository.registrar(payload);
    }
}
