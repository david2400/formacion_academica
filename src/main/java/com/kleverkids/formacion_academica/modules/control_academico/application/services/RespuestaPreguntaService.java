package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ActualizarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ConsultarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.EliminarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ListarRespuestasPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.RegistrarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen.EstudianteExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RespuestaPreguntaService implements RegistrarRespuestaPreguntaUseCase,
        ActualizarRespuestaPreguntaUseCase,
        ListarRespuestasPreguntaUseCase,
        ConsultarRespuestaPreguntaUseCase,
        EliminarRespuestaPreguntaUseCase {

    private final RespuestaPreguntaRepositoryPort respuestaRepository;
    private final EstudianteExamenRepositoryPort estudianteExamenRepository;

    @Override
    public RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaDto request) {
        UUID examenId = request.examenId();
        UUID estudianteId = request.estudianteId();
        EstudianteExamenDto relacion = estudianteExamenRepository.buscarPorExamenYEstudiante(examenId, estudianteId)
                .orElseGet(() -> estudianteExamenRepository.registrar(new RegistrarEstudianteExamenDto(examenId, estudianteId)));

        RegistrarRespuestaPreguntaPersistenceDto payload = new RegistrarRespuestaPreguntaPersistenceDto(
                relacion.id(),
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

    @Override
    public RespuestaPreguntaDto actualizar(ActualizarRespuestaPreguntaDto request) {
        return respuestaRepository.actualizar(request);
    }

    @Override
    public List<RespuestaPreguntaDto> listar(UUID examenId, UUID estudianteId) {
        return respuestaRepository.listarPorEstudiante(examenId, estudianteId);
    }

    @Override
    public RespuestaPreguntaDto consultarPorId(UUID respuestaId) {
        return respuestaRepository.obtenerPorId(respuestaId);
    }

    @Override
    public void eliminar(UUID respuestaId) {
        respuestaRepository.obtenerPorId(respuestaId);
        respuestaRepository.eliminar(respuestaId);
    }
}
