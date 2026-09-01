package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.CrearExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.EliminarExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.ListarExamenTematicasPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
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

@Description(value = "Gestiona las temáticas asignadas a un examen")
@Tag(name = "Examen Temática", description = "Gestiona las temáticas asignadas a un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/tematicas")
public class ExamenTematicaController {

    private final CrearExamenTematicaUseCase asignarUseCase;
    private final ListarExamenTematicasPorExamenUseCase listarUseCase;
    private final EliminarExamenTematicaUseCase eliminarUseCase;

    @Operation(summary = "Asignar temática", description = "Asigna una temática a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Temática asignada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamenTematicaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "La temática ya está asignada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ExamenTematicaDto> asignar(@PathVariable Long examenId,
                                                     @Valid @RequestBody CrearExamenTematicaDto request) {
        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(asignarUseCase.asignar(request));
    }

    @Operation(summary = "Listar temáticas", description = "Obtiene las temáticas asignadas a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de temáticas asignadas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamenTematicaDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ExamenTematicaDto>> listar(@PathVariable Long examenId) {
        return ResponseEntity.ok(listarUseCase.listarPorExamen(examenId));
    }

    @Operation(summary = "Desasignar temática", description = "Elimina la asignación de una temática a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Temática desasignada"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{tematicaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long tematicaId) {
        eliminarUseCase.eliminar(examenId, tematicaId);
        return ResponseEntity.noContent().build();
    }
}
