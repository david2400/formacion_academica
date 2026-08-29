package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ActualizarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ConsultarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.CrearExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.EliminarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ListarExamenCriterioPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.CrearExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
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

@Description(value = "Gestiona la asignación de criterios a un examen")
@Tag(name = "Examen Criterio", description = "Gestiona la asignación de criterios a un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/criterios")
public class ExamenCriterioController {

    private final CrearExamenCriterioUseCase crearUseCase;
    private final ActualizarExamenCriterioUseCase actualizarUseCase;
    private final ListarExamenCriterioPorExamenUseCase listarUseCase;
    private final ConsultarExamenCriterioUseCase consultarUseCase;
    private final EliminarExamenCriterioUseCase eliminarUseCase;

    @Operation(summary = "Asignar criterio", description = "Asigna un criterio evaluado a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asignación creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenCriterioDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ExamenCriterioDto> crear(@PathVariable Long examenId,
                                                   @Valid @RequestBody CrearExamenCriterioDto request) {
        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar asignación", description = "Actualiza la ponderación u orden de un criterio asignado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenCriterioDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{asignacionId}")
    public ResponseEntity<ExamenCriterioDto> actualizar(@PathVariable Long examenId,
                                                        @PathVariable Long asignacionId,
                                                        @Valid @RequestBody ActualizarExamenCriterioDto request) {
        request.setId(asignacionId);
        request.setExamenId(examenId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar criterios asignados", description = "Obtiene los criterios asignados a un examen ordenados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de asignaciones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamenCriterioDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ExamenCriterioDto>> listar(@PathVariable Long examenId) {
        return ResponseEntity.ok(listarUseCase.listarPorExamen(examenId));
    }

    @Operation(summary = "Consultar asignación", description = "Obtiene la información de una asignación específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenCriterioDto.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{asignacionId}")
    public ResponseEntity<ExamenCriterioDto> consultar(@PathVariable Long examenId,
                                                       @PathVariable Long asignacionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(asignacionId));
    }

    @Operation(summary = "Eliminar asignación", description = "Elimina la asignación de un criterio a un examen")
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
