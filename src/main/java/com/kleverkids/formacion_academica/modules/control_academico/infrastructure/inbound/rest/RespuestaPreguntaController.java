package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ActualizarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ConsultarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.EliminarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.ListarRespuestasPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta.RegistrarRespuestaPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona las respuestas a preguntas de un examen")
@Tag(name = "Respuestas Preguntas", description = "Gestiona las respuestas a preguntas de un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/respuestas-preguntas")
public class RespuestaPreguntaController {

    private final RegistrarRespuestaPreguntaUseCase registrarUseCase;
    private final ActualizarRespuestaPreguntaUseCase actualizarUseCase;
    private final ListarRespuestasPreguntaUseCase listarUseCase;
    private final ConsultarRespuestaPreguntaUseCase consultarUseCase;
    private final EliminarRespuestaPreguntaUseCase eliminarUseCase;


    @Operation(summary = "Registrar respuesta", description = "Crea una nueva respuesta a la pregunta del examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Respuesta registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespuestaPreguntaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RespuestaPreguntaDto> registrar(@PathVariable Long examenId,
                                                          @PathVariable Long estudianteId,
                                                          @Valid @RequestBody RegistrarRespuestaPreguntaDto request) {
        request.setExamenId(examenId);
        request.setEstudianteId(estudianteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @Operation(summary = "Actualizar respuesta", description = "Actualiza la respuesta registrada por el estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespuestaPreguntaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{respuestaId}")
    public ResponseEntity<RespuestaPreguntaDto> actualizar(@PathVariable Long respuestaId,
                                                           @Valid @RequestBody ActualizarRespuestaPreguntaDto request) {
        request.setId(respuestaId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar respuestas", description = "Obtiene todas las respuestas del estudiante para el examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de respuestas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RespuestaPreguntaDto.class)))),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<RespuestaPreguntaDto>> listar(@PathVariable Long examenId,
                                                             @PathVariable Long estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }

    @Operation(summary = "Consultar respuesta", description = "Obtiene los detalles de una respuesta puntual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespuestaPreguntaDto.class))),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{respuestaId}")
    public ResponseEntity<RespuestaPreguntaDto> consultar(@PathVariable Long examenId,
                                                          @PathVariable Long estudianteId,
                                                          @PathVariable Long respuestaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(respuestaId));
    }

    @Operation(summary = "Eliminar respuesta", description = "Elimina la respuesta registrada")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Respuesta eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{respuestaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long estudianteId,
                                         @PathVariable Long respuestaId) {
        eliminarUseCase.eliminar(respuestaId);
        return ResponseEntity.noContent().build();
    }
}
