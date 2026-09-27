package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ActualizarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ConsultarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.CrearExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.EliminarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ListarExamenPreguntaPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
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

@Description(value = "Gestiona la asignación de preguntas a un examen")
@Tag(name = "Examen Pregunta", description = "Gestiona la asignación de preguntas a un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/preguntas")
public class ExamenPreguntaController {

    private final CrearExamenPreguntaUseCase crearUseCase;
    private final ActualizarExamenPreguntaUseCase actualizarUseCase;
    private final ListarExamenPreguntaPorExamenUseCase listarUseCase;
    private final ConsultarExamenPreguntaUseCase consultarUseCase;
    private final EliminarExamenPreguntaUseCase eliminarUseCase;

    @Operation(summary = "Asignar pregunta", description = "Asigna una pregunta del banco a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asignación creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenPreguntaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ExamenPreguntaDto> crear(@PathVariable Long examenId,
                                                    @Valid @RequestBody CrearExamenPreguntaDto request) {
        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar asignación", description = "Actualiza el orden o los puntos de una pregunta asignada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenPreguntaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{asignacionId}")
    public ResponseEntity<ExamenPreguntaDto> actualizar(@PathVariable Long examenId,
                                                         @PathVariable Long asignacionId,
                                                         @Valid @RequestBody ActualizarExamenPreguntaDto request) {
        request.setId(asignacionId);
        request.setExamenId(examenId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar preguntas asignadas", description = "Obtiene las preguntas asignadas a un examen, ordenadas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de asignaciones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamenPreguntaDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ExamenPreguntaDto>> listar(@PathVariable Long examenId) {
        return ResponseEntity.ok(listarUseCase.listarPorExamen(examenId));
    }

    @Operation(summary = "Consultar asignación", description = "Obtiene la información de una asignación específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenPreguntaDto.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{asignacionId}")
    public ResponseEntity<ExamenPreguntaDto> consultar(@PathVariable Long examenId,
                                                        @PathVariable Long asignacionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(asignacionId));
    }

    @Operation(summary = "Eliminar asignación", description = "Elimina la asignación de una pregunta a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asignación eliminada"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{asignacionId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long asignacionId) {
        eliminarUseCase.eliminar(asignacionId);
        return ResponseEntity.noContent().build();
    }
}
