package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ActualizarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ListarRespuestasPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.RegistrarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
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

@Description(value = "Gestiona las respuestas a preguntas de un examen")
@Tag(name = "Respuestas Preguntas", description = "Gestiona las respuestas a preguntas de un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/respuestas-preguntas")
public class RespuestaPreguntaController {

    private final RegistrarRespuestaPreguntaUseCase registrarUseCase;
    private final ActualizarRespuestaPreguntaUseCase actualizarUseCase;
    private final ListarRespuestasPreguntaUseCase listarUseCase;


    @PostMapping
    public ResponseEntity<RespuestaPreguntaDto> registrar(@PathVariable UUID examenId,
                                                          @PathVariable UUID estudianteId,
                                                          @Valid @RequestBody RegistrarRespuestaPreguntaDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @PutMapping("/{respuestaId}")
    public ResponseEntity<RespuestaPreguntaDto> actualizar(@PathVariable UUID respuestaId,
                                                           @Valid @RequestBody ActualizarRespuestaPreguntaDto request) {

        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<RespuestaPreguntaDto>> listar(@PathVariable UUID examenId,
                                                             @PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }
}
