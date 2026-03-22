package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ConsultarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.EliminarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
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

@Description(value = "Gestiona las temáticas asociadas a un examen")
@Tag(name = "Temáticas", description = "Gestiona las temáticas asociadas a un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/tematicas")
public class TematicaController {

    private final CrearTematicaUseCase crearUseCase;
    private final ActualizarTematicaUseCase actualizarUseCase;
    private final ListarTematicasUseCase listarUseCase;
    private final ConsultarTematicaUseCase consultarUseCase;
    private final EliminarTematicaUseCase eliminarUseCase;

    @Operation(summary = "Crear temática", description = "Registra una nueva temática asociada al examen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Temática creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tematica.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Tematica> crear(@PathVariable Long examenId,
                                                   @Valid @RequestBody CrearTematicaDto request) {

        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar temática", description = "Actualiza los datos de una temática existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Temática actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tematica.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Temática no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{tematicaId}")
    public ResponseEntity<Tematica> actualizar(@PathVariable Long examenId,
                                                        @PathVariable Long tematicaId,
                                                        @Valid @RequestBody ActualizarTematicaDto request) {

        request.setExamenId(examenId);
        request.setId(tematicaId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Listar temáticas", description = "Obtiene todas las temáticas asociadas al examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de temáticas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tematica.class)))),
            @ApiResponse(responseCode = "404", description = "Examen no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Tematica>> listar(@PathVariable Long examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }

    @Operation(summary = "Consultar temática", description = "Obtiene los detalles de una temática específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Temática encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tematica.class))),
            @ApiResponse(responseCode = "404", description = "Temática no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{tematicaId}")
    public ResponseEntity<Tematica> consultar(@PathVariable Long examenId,
                                                 @PathVariable Long tematicaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(tematicaId));
    }

    @Operation(summary = "Eliminar temática", description = "Elimina una temática asociada al examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Temática eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Temática no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{tematicaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long examenId,
                                         @PathVariable Long tematicaId) {
        eliminarUseCase.eliminar(tematicaId);
        return ResponseEntity.noContent().build();
    }
}
