package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tipo_clase.GestionarTiposClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.ActualizarTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tipos de clase", description = "Catálogo de tipos de clase")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/tipos-clase")
public class TipoClaseController {

    private final GestionarTiposClaseUseCase gestionarTiposClaseUseCase;

    @Operation(summary = "Crear tipo de clase")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo creado",
                    content = @Content(schema = @Schema(implementation = TipoClase.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre duplicado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TipoClase> crear(@Valid @RequestBody CrearTipoClaseDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gestionarTiposClaseUseCase.crear(request));
    }

    @Operation(summary = "Listar tipos de clase")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Listado de tipos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoClase.class)))))
    @GetMapping
    public ResponseEntity<List<TipoClase>> listar() {
        return ResponseEntity.ok(gestionarTiposClaseUseCase.listar());
    }

    @Operation(summary = "Obtener tipo de clase por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo encontrado",
                    content = @Content(schema = @Schema(implementation = TipoClase.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado", content = @Content)
    })
    @GetMapping("/{tipoClaseId}")
    public ResponseEntity<TipoClase> consultar(@PathVariable Long tipoClaseId) {
        return gestionarTiposClaseUseCase.consultarPorId(tipoClaseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar tipo de clase")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo actualizado",
                    content = @Content(schema = @Schema(implementation = TipoClase.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado", content = @Content)
    })
    @PutMapping("/{tipoClaseId}")
    public ResponseEntity<TipoClase> actualizar(@PathVariable Long tipoClaseId,
                                                @Valid @RequestBody ActualizarTipoClaseDto request) {
        return ResponseEntity.ok(gestionarTiposClaseUseCase.actualizar(tipoClaseId, request));
    }

    @Operation(summary = "Eliminar tipo de clase", description = "Borrado lógico del tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo eliminado"),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado", content = @Content)
    })
    @DeleteMapping("/{tipoClaseId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long tipoClaseId) {
        gestionarTiposClaseUseCase.eliminar(tipoClaseId);
        return ResponseEntity.noContent().build();
    }
}
