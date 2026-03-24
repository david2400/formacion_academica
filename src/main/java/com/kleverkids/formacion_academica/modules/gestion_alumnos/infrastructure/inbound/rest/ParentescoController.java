package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.ParentescoService;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Description(value = "Gestiona los parentescos")
@Tag(name = "Parentescos", description = "Gestiona los parentescos")
@RestController
@RequestMapping("/gestion-alumnos/parentescos")
public class ParentescoController {

    private final ParentescoService parentescoService;

    public ParentescoController(ParentescoService parentescoService) {
        this.parentescoService = parentescoService;
    }

    @Operation(summary = "Crear parentesco", description = "Registra un nuevo parentesco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Parentesco creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Parentesco.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Parentesco> crear(@Valid @RequestBody CrearParentescoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parentescoService.crear(request));
    }

    @Operation(summary = "Actualizar parentesco", description = "Actualiza los datos de un parentesco existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parentesco actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Parentesco.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parentesco no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{parentescoId}")
    public ResponseEntity<Parentesco> actualizar(@PathVariable Long parentescoId,
                                                    @Valid @RequestBody ActualizarParentescoDto request) {

        return ResponseEntity.ok(parentescoService.actualizar(request));
    }

    @Operation(summary = "Consultar parentesco", description = "Obtiene la información de un parentesco por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parentesco encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Parentesco.class))),
            @ApiResponse(responseCode = "404", description = "Parentesco no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{parentescoId}")
    public ResponseEntity<Parentesco> consultar(@PathVariable Long parentescoId) {
        return parentescoService.consultarPorId(parentescoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar parentescos", description = "Obtiene el catálogo completo de parentescos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de parentescos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Parentesco.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Parentesco>> listar() {
        return ResponseEntity.ok(parentescoService.listarTodos());
    }

    @Operation(summary = "Listar parentescos activos", description = "Obtiene el catálogo de parentescos activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de parentescos activos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Parentesco.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/activos")
    public ResponseEntity<List<Parentesco>> listarActivos() {
        return ResponseEntity.ok(parentescoService.listarActivos());
    }

    @Operation(summary = "Eliminar parentesco", description = "Elimina un parentesco")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Parentesco eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parentesco no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{parentescoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long parentescoId) {
        parentescoService.eliminar(parentescoId);
        return ResponseEntity.noContent().build();
    }
}
