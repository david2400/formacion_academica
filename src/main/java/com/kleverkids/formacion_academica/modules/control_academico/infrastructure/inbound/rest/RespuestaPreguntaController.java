package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ActualizarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ListarRespuestasPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.RegistrarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/respuestas-preguntas")
public class RespuestaPreguntaController {

    private final RegistrarRespuestaPreguntaUseCase registrarUseCase;
    private final ActualizarRespuestaPreguntaUseCase actualizarUseCase;
    private final ListarRespuestasPreguntaUseCase listarUseCase;

    public RespuestaPreguntaController(RegistrarRespuestaPreguntaUseCase registrarUseCase,
                                       ActualizarRespuestaPreguntaUseCase actualizarUseCase,
                                       ListarRespuestasPreguntaUseCase listarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<RespuestaPreguntaDto> registrar(@PathVariable UUID examenId,
                                                          @PathVariable UUID estudianteId,
                                                          @RequestBody RegistrarRespuestaPreguntaDto request) {
        RegistrarRespuestaPreguntaDto payload = new RegistrarRespuestaPreguntaDto(
                examenId,
                estudianteId,
                request.preguntaId(),
                request.respuestaBancoId(),
                request.respuestaTexto(),
                request.esCorrecta(),
                request.puntajeObtenido()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(payload));
    }

    @PutMapping("/{respuestaId}")
    public ResponseEntity<RespuestaPreguntaDto> actualizar(@PathVariable UUID respuestaId,
                                                           @RequestBody ActualizarRespuestaPreguntaDto request) {
        ActualizarRespuestaPreguntaDto payload = new ActualizarRespuestaPreguntaDto(
                respuestaId,
                request.respuestaTexto(),
                request.respuestaBancoId(),
                request.esCorrecta(),
                request.puntajeObtenido()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping
    public ResponseEntity<List<RespuestaPreguntaDto>> listar(@PathVariable UUID examenId,
                                                             @PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }
}
