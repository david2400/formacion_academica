package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ActualizarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ConsultarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.CrearCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.EliminarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
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

@Description(value = "Gestiona los criterios de evaluación de un examen")
@Tag(name = "Criterios de Examen", description = "Gestiona los criterios de evaluación de un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/criterios")
public class CriterioExamenController {

    private final CrearCriterioExamenUseCase crearUseCase;
    private final ActualizarCriterioExamenUseCase actualizarUseCase;
    private final ListarCriteriosPorExamenUseCase listarUseCase;
    private final ConsultarCriterioExamenUseCase consultarUseCase;
    private final EliminarCriterioExamenUseCase eliminarUseCase;

    @Operation(summary = "Crear criterio", description = "Agrega un nuevo criterio al examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criterio creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CriterioExamenDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CriterioExamenDto> crear(@PathVariable Long examenId,
                                                   @Valid @RequestBody CrearCriterioExamenDto request) {
        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar criterio", description = "Actualiza los datos de un criterio existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Criterio actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CriterioExamenDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Criterio no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{criterioId}")
    public ResponseEntity<CriterioExamenDto> actualizar(@PathVariable Long examenId,
                                                        @PathVariable Long criterioId,
                                                        @Valid @RequestBody ActualizarCriterioExamenDto request) {
        request.setExamenId(examenId);
        request.setId(criterioId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar criterios", description = "Obtiene todos los criterios asociados a un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de criterios",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CriterioExamenDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CriterioExamenDto>> listar(@PathVariable Long examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }

    @Operation(summary = "Consultar criterio", description = "Obtiene la información de un criterio específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Criterio encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CriterioExamenDto.class))),
            @ApiResponse(responseCode = "404", description = "Criterio no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{criterioId}")
    public ResponseEntity<CriterioExamenDto> consultar(@PathVariable Long examenId,
                                                       @PathVariable Long criterioId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(criterioId));
    }

    @Operation(summary = "Eliminar criterio", description = "Elimina un criterio del examen")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Criterio eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Criterio no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{criterioId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long criterioId) {
        eliminarUseCase.eliminar(criterioId);
        return ResponseEntity.noContent().build();
    }
}
