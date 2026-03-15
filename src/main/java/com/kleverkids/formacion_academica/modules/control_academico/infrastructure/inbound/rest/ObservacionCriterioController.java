package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ActualizarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ConsultarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.EliminarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ListarObservacionesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.RegistrarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
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


@Description(value = "Gestiona las observaciones asociadas a criterios de examen")
@Tag(name = "Observaciones", description = "Gestiona las observaciones asociadas a criterios de examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/observaciones")
public class ObservacionCriterioController {

    private final RegistrarObservacionCriterioUseCase registrarUseCase;
    private final ActualizarObservacionCriterioUseCase actualizarUseCase;
    private final ListarObservacionesPorEstudianteUseCase listarUseCase;
    private final ConsultarObservacionCriterioUseCase consultarUseCase;
    private final EliminarObservacionCriterioUseCase eliminarUseCase;

    @Operation(summary = "Registrar observación", description = "Crea una observación para el criterio de un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Observación registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ObservacionCriterioDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ObservacionCriterioDto> registrar(@PathVariable Long examenId,
                                                            @PathVariable Long estudianteId,
                                                            @Valid @RequestBody RegistrarObservacionCriterioDto request) {
        request.setExamenId(examenId);
        request.setEstudianteId(estudianteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @Operation(summary = "Actualizar observación", description = "Actualiza una observación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Observación actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ObservacionCriterioDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Observación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{observacionId}")
    public ResponseEntity<ObservacionCriterioDto> actualizar(@PathVariable Long examenId,
                                                             @PathVariable Long estudianteId,
                                                             @PathVariable Long observacionId,
                                                             @Valid @RequestBody ActualizarObservacionCriterioDto request) {
        request.setExamenId(examenId);
        request.setEstudianteId(estudianteId);
        request.setId(observacionId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar observaciones", description = "Obtiene todas las observaciones de un estudiante en un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de observaciones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ObservacionCriterioDto.class)))),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ObservacionCriterioDto>> listar(@PathVariable Long examenId,
                                                               @PathVariable Long estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }

    @Operation(summary = "Consultar observación", description = "Obtiene los detalles de una observación específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Observación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ObservacionCriterioDto.class))),
            @ApiResponse(responseCode = "404", description = "Observación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{observacionId}")
    public ResponseEntity<ObservacionCriterioDto> consultar(@PathVariable Long examenId,
                                                            @PathVariable Long estudianteId,
                                                            @PathVariable Long observacionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(observacionId));
    }

    @Operation(summary = "Eliminar observación", description = "Elimina una observación registrada")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Observación eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Observación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{observacionId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long estudianteId,
                                         @PathVariable Long observacionId) {
        eliminarUseCase.eliminar(observacionId);
        return ResponseEntity.noContent().build();
    }
}
