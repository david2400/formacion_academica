package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.CrearActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.EliminarActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.ListarActivityTematicasPorActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Gestiona las temáticas asignadas a una actividad (catálogo compartido con
 * preguntas y exámenes, ver {@code Tematica}). Mismo contrato que
 * {@code ExamenTematicaController}, para que el frontend reutilice el mismo
 * patrón de sincronización (agregar/quitar) ya usado en exámenes.
 */
@Description(value = "Gestiona las temáticas asignadas a una actividad")
@Tag(name = "Activity Temática", description = "Gestiona las temáticas asignadas a una actividad")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/activities/{activityId}/tematicas")
public class ActivityTematicaController {

    private final CrearActivityTematicaUseCase asignarUseCase;
    private final ListarActivityTematicasPorActivityUseCase listarUseCase;
    private final EliminarActivityTematicaUseCase eliminarUseCase;

    @Operation(summary = "Asignar temática", description = "Asigna una temática a una actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Temática asignada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityTematicaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "La temática ya está asignada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityTematicaDto> asignar(@PathVariable Long activityId,
                                                        @Valid @RequestBody CrearActivityTematicaDto request) {
        request.setActivityId(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(asignarUseCase.asignar(request));
    }

    @Operation(summary = "Listar temáticas", description = "Obtiene las temáticas asignadas a una actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de temáticas asignadas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityTematicaDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ActivityTematicaDto>> listar(@PathVariable Long activityId) {
        return ResponseEntity.ok(listarUseCase.listarPorActivity(activityId));
    }

    @Operation(summary = "Desasignar temática", description = "Elimina la asignación de una temática a una actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Temática desasignada"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{tematicaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long activityId,
                                         @PathVariable Long tematicaId) {
        eliminarUseCase.eliminar(activityId, tematicaId);
        return ResponseEntity.noContent().build();
    }
}
