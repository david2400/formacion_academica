package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RestController
@RequestMapping("/estructura-institucion/sedes")
@RequiredArgsConstructor
@Tag(name = "Sedes", description = "API para la gestión de sedes institucionales")
public class SedeController {

    private final CrearSedeUseCase crearSedeUseCase;
    private final ActualizarSedeUseCase actualizarSedeUseCase;
    private final ConsultarSedeUseCase consultarSedeUseCase;
    private final ListarSedesUseCase listarSedesUseCase;
    private final EliminarSedeUseCase eliminarSedeUseCase;

    @PostMapping
    @Operation(
        summary = "Crear una nueva sede",
        description = "Crea una nueva sede institucional con su información completa de contacto y ubicación"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Sede creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Sede.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe una sede con el mismo código",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Sede> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la sede a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = CrearSedeDto.class))
            )
            @Valid @RequestBody CrearSedeDto dto) {
        Sede creada = crearSedeUseCase.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar sede por ID",
        description = "Obtiene los detalles completos de una sede específica mediante su identificador único"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Sede encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Sede.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Sede no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Sede> buscarPorId(
            @Parameter(description = "ID único de la sede", required = true, example = "1")
            @PathVariable Long id) {
        return consultarSedeUseCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/codigo/{codigo}")
//    @Operation(
//        summary = "Buscar sede por código",
//        description = "Obtiene una sede mediante su código único alfanumérico"
//    )
//    @ApiResponses({
//        @ApiResponse(
//            responseCode = "200",
//            description = "Sede encontrada",
//            content = @Content(
//                mediaType = "application/json",
//                schema = @Schema(implementation = Sede.class)
//            )
//        ),
//        @ApiResponse(
//            responseCode = "404",
//            description = "Sede no encontrada",
//            content = @Content
//        ),
//        @ApiResponse(
//            responseCode = "500",
//            description = "Error interno del servidor",
//            content = @Content
//        )
//    })
//    public ResponseEntity<Sede> buscarPorCodigo(
//            @Parameter(description = "Código único de la sede", required = true, example = "SEDE01")
//            @PathVariable String codigo) {
//        return consultarSedeUseCase.buscarPorCodigo(codigo)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping
    @Operation(
        summary = "Listar todas las sedes",
        description = "Obtiene una lista completa de todas las sedes registradas en el sistema, " +
                     "incluyendo activas e inactivas"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de sedes obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Sede.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<Sede>> listarTodas() {
        List<Sede> sedes = listarSedesUseCase.listarTodas();
        return ResponseEntity.ok(sedes);
    }

    @GetMapping("/activas")
    @Operation(
        summary = "Listar sedes activas",
        description = "Obtiene únicamente las sedes que están marcadas como activas en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de sedes activas obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Sede.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<Sede>> listarActivas() {
        List<Sede> sedes = listarSedesUseCase.listarActivas();
        return ResponseEntity.ok(sedes);
    }

//    @GetMapping("/ciudad/{ciudad}")
//    @Operation(
//        summary = "Listar sedes por ciudad",
//        description = "Obtiene las sedes activas que se encuentran en una ciudad específica"
//    )
//    @ApiResponses({
//        @ApiResponse(
//            responseCode = "200",
//            description = "Lista de sedes de la ciudad obtenida exitosamente",
//            content = @Content(
//                mediaType = "application/json",
//                array = @ArraySchema(schema = @Schema(implementation = Sede.class))
//            )
//        ),
//        @ApiResponse(
//            responseCode = "500",
//            description = "Error interno del servidor",
//            content = @Content
//        )
//    })
//    public ResponseEntity<List<Sede>> listarPorCiudad(
//            @Parameter(description = "Nombre de la ciudad a buscar", required = true, example = "Bogotá")
//            @PathVariable String ciudad) {
//        List<Sede> sedes = listarSedesUseCase.listarPorCiudad(ciudad);
//        return ResponseEntity.ok(sedes);
//    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar sede",
        description = "Actualiza los datos de una sede existente. " +
                     "Permite modificar información de contacto y ubicación"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Sede actualizada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Sede.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Sede no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflicto al actualizar",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Sede> actualizar(
            @Parameter(description = "ID de la sede a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados de la sede",
                required = true,
                content = @Content(schema = @Schema(implementation = ActualizarSedeDto.class))
            )
            @Valid @RequestBody ActualizarSedeDto dto) {
        try {
            Sede actualizada = actualizarSedeUseCase.actualizar(dto);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar sede",
        description = "Elimina una sede del sistema. " +
                     "Solo se puede eliminar si no tiene dependencias (salones, etc.)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Sede eliminada exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Sede no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "No se puede eliminar porque tiene dependencias",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sede a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        boolean eliminado = eliminarSedeUseCase.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
