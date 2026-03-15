package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Description(value = "Gestiona los exámenes")
@Tag(name = "Exámenes", description = "Gestiona los exámenes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes")
public class ExamenController {
    
    // Use Cases existentes
    private final CrearExamenUseCase crearExamenUseCase;
    private final ConsultarExamenUseCase consultarExamenUseCase;
    private final ActualizarExamenUseCase actualizarExamenUseCase;
    private final EliminarExamenUseCase eliminarExamenUseCase;
    private final BuscarExamenesUseCase buscarExamenesUseCase;
    private final IniciarExamenUseCase iniciarExamenUseCase;
    private final EnviarExamenUseCase enviarExamenUseCase;
    private final CalificarExamenUseCase calificarExamenUseCase;
    private final ObtenerResultadosExamenUseCase obtenerResultadosExamenUseCase;
    private final RegistrarCalificacionPersonalizadaUseCase registrarCalificacionPersonalizadaUseCase;

    // Endpoints básicos
    @Operation(summary = "Crear examen", description = "Registra un nuevo examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Examen creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ExamenDto> crear(@Valid @RequestBody CrearExamenDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamenUseCase.crear(request));
    }
    
    @Operation(summary = "Consultar examen", description = "Obtiene la información detallada de un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{examenId}")
    public ResponseEntity<ExamResponse> consultar(@PathVariable Long examenId) {
        return ResponseEntity.ok(consultarExamenUseCase.consultarPorId(examenId));
    }
    
    @Operation(summary = "Actualizar examen", description = "Actualiza los datos de un examen existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{examenId}")
    public ResponseEntity<ExamResponse> actualizar(@PathVariable Long examenId, @Valid @RequestBody UpdateExamCommand command) {
        return ResponseEntity.ok(actualizarExamenUseCase.actualizar(examenId, command));
    }
    
    @Operation(summary = "Eliminar examen", description = "Elimina un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Examen eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{examenId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId) {
        eliminarExamenUseCase.eliminar(examenId);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Registrar calificación personalizada", description = "Registra calificaciones personales sobre un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Calificación registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CalificacionPersonalizadaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/calificaciones")
    public ResponseEntity<CalificacionPersonalizadaDto> registrarCalificacion(@Valid @RequestBody RegistrarCalificacionPersonalizadaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarCalificacionPersonalizadaUseCase.registrar(request));
    }
    
    // Endpoints adicionales del ExamController original
    @Operation(summary = "Iniciar examen", description = "Crea un envío (submission) para iniciar el examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Examen iniciado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamSubmissionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/{examenId}/iniciar")
    public ResponseEntity<ExamSubmissionResponse> iniciar(
            @PathVariable Long examenId,
            @RequestParam Long estudianteId) {
        ExamSubmissionResponse response = iniciarExamenUseCase.iniciar(examenId, estudianteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Enviar examen", description = "Entrega las respuestas de un examen para ser calificadas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen entregado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/{examenId}/enviar")
    public ResponseEntity<ExamResultResponse> enviar(
            @PathVariable Long examenId,
            @RequestBody SubmitExamCommand command) {
        ExamResultResponse response = enviarExamenUseCase.enviar(examenId, command);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Calificar envío", description = "Registra la calificación manual de un intento de examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen calificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultResponse.class))),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/{examenId}/envios/{envioId}/calificar")
    public ResponseEntity<ExamResultResponse> calificar(
            @PathVariable Long examenId,
            @PathVariable Long envioId,
            @RequestBody GradeExamCommand command) {
        ExamResultResponse response = calificarExamenUseCase.calificar(examenId, envioId, command);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Listar resultados", description = "Obtiene los resultados de todos los estudiantes para un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados del examen",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamResultResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{examenId}/resultados")
    public ResponseEntity<List<ExamResultResponse>> getResultados(@PathVariable Long examenId) {
        List<ExamResultResponse> response = obtenerResultadosExamenUseCase.obtenerResultados(examenId);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Consultar resultado de estudiante", description = "Obtiene la calificación de un estudiante específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultResponse.class))),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{examenId}/resultados/{estudianteId}")
    public ResponseEntity<ExamResultResponse> getResultadoEstudiante(
            @PathVariable Long examenId,
            @PathVariable Long estudianteId) {
        ExamResultResponse response = obtenerResultadosExamenUseCase.obtenerResultadoEstudiante(examenId, estudianteId);
        return ResponseEntity.ok(response);
    }
}
