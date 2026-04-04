package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.NivelEducativoService;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.NivelEducativoEstadisticasDto;
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
@RequestMapping("/estructura-institucion/niveles-educativos")
@RequiredArgsConstructor
@Tag(name = "Niveles Educativos", description = "API para la gestión de niveles educativos según la circular colombiana 2025")
public class NivelEducativoController {

    private final NivelEducativoService service;

    @PostMapping
    @Operation(
        summary = "Crear un nuevo nivel educativo",
        description = "Crea un nuevo nivel educativo según la estructura de la circular colombiana 2025. " +
                     "Los niveles educativos representan la estructura jerárquica del sistema educativo."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Nivel educativo creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NivelEducativo.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe un nivel educativo con el mismo código",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<NivelEducativo> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nivel educativo a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = CrearNivelEducativoDto.class))
            )
            @Valid @RequestBody CrearNivelEducativoDto dto) {
        NivelEducativo creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar nivel educativo por ID",
        description = "Obtiene los detalles completos de un nivel educativo específico mediante su identificador único"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Nivel educativo encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NivelEducativo.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Nivel educativo no encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<NivelEducativo> buscarPorId(
            @Parameter(description = "ID único del nivel educativo", required = true, example = "1")
            @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(
        summary = "Buscar nivel educativo por código",
        description = "Obtiene un nivel educativo mediante su código único alfanumérico"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Nivel educativo encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NivelEducativo.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Nivel educativo no encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<NivelEducativo> buscarPorCodigo(
            @Parameter(description = "Código único del nivel educativo", required = true, example = "PREESCOLAR")
            @PathVariable String codigo) {
        return service.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(
        summary = "Listar todos los niveles educativos",
        description = "Obtiene una lista completa de todos los niveles educativos registrados en el sistema, " +
                     "incluyendo activos e inactivos"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de niveles educativos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = NivelEducativo.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<NivelEducativo>> listarTodos() {
        List<NivelEducativo> niveles = service.listarTodos();
        return ResponseEntity.ok(niveles);
    }

//    @GetMapping("/categoria/{categoria}")
//    @Operation(summary = "Listar niveles por categoría", description = "Obtiene niveles educativos filtrados por categoría (PREESCOLAR, BASICA, MEDIA, SUPERIOR)")
//    public ResponseEntity<List<NivelEducativo>> listarPorCategoria(
//            @Parameter(description = "Categoría del nivel educativo") @PathVariable String categoria) {
//        List<NivelEducativo> niveles = service.listarPorCategoria(categoria);
//        return ResponseEntity.ok(niveles);
//    }

    @GetMapping("/nivel-superior/{nivelSuperiorId}")
    @Operation(
        summary = "Listar niveles por nivel superior",
        description = "Obtiene todos los niveles educativos que dependen jerárquicamente de un nivel superior específico"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de niveles educativos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = NivelEducativo.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<NivelEducativo>> listarPorNivelSuperior(
            @Parameter(description = "ID del nivel superior del cual dependen los niveles a buscar", required = true, example = "1")
            @PathVariable Long nivelSuperiorId) {
        List<NivelEducativo> niveles = service.listarPorNivelSuperior(nivelSuperiorId);
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/activos")
    @Operation(
        summary = "Listar niveles activos",
        description = "Obtiene únicamente los niveles educativos que están marcados como activos en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de niveles educativos activos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = NivelEducativo.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<NivelEducativo>> listarActivos() {
        List<NivelEducativo> niveles = service.listarActivos();
        return ResponseEntity.ok(niveles);
    }

//    @GetMapping("/categoria-activos/{categoria}")
//    @Operation(summary = "Listar niveles activos por categoría", description = "Obtiene niveles educativos activos filtrados por categoría")
//    public ResponseEntity<List<NivelEducativo>> listarPorCategoriaActivos(
//            @Parameter(description = "Categoría del nivel educativo") @PathVariable String categoria) {
//        List<NivelEducativo> niveles = service.listarPorCategoriaActivos(categoria);
//        return ResponseEntity.ok(niveles);
//    }

    @GetMapping("/principales")
    @Operation(
        summary = "Listar niveles principales",
        description = "Obtiene los niveles educativos principales (raíz) que no tienen un nivel superior, " +
                     "es decir, los niveles en el primer nivel de la jerarquía"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de niveles educativos principales obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = NivelEducativo.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<List<NivelEducativo>> listarNivelesPrincipales() {
        List<NivelEducativo> niveles = service.listarNivelesPrincipales();
        return ResponseEntity.ok(niveles);
    }

//    @GetMapping("/estadisticas")
//    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas completas de los niveles educativos")
//    public ResponseEntity<NivelEducativoEstadisticasDto> obtenerEstadisticas() {
//        NivelEducativoEstadisticasDto estadisticas = service.obtenerEstadisticas();
//        return ResponseEntity.ok(estadisticas);
//    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar nivel educativo",
        description = "Actualiza los datos de un nivel educativo existente. " +
                     "Permite modificar nombre, descripción, orden y nivel superior"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Nivel educativo actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NivelEducativo.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Nivel educativo no encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflicto al actualizar (ej: código duplicado)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<NivelEducativo> actualizar(
            @Parameter(description = "ID del nivel educativo a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados del nivel educativo",
                required = true,
                content = @Content(schema = @Schema(implementation = ActualizarNivelEducativoDto.class))
            )
            @Valid @RequestBody ActualizarNivelEducativoDto dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar nivel educativo",
        description = "Elimina un nivel educativo del sistema. " +
                     "Solo se puede eliminar si no tiene dependencias (grados, niveles inferiores, etc.)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Nivel educativo eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Nivel educativo no encontrado",
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
            @Parameter(description = "ID del nivel educativo a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
