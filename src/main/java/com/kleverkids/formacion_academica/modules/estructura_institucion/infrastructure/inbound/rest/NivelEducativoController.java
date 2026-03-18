package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.NivelEducativoService;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoEstadisticasDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/niveles-educativos")
@RequiredArgsConstructor
@Tag(name = "Niveles Educativos", description = "API para la gestión de niveles educativos según la circular colombiana 2025")
public class NivelEducativoController {

    private final NivelEducativoService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo nivel educativo", description = "Crea un nuevo nivel educativo según la estructura de la circular colombiana 2025")
    public ResponseEntity<NivelEducativoDto> crear(@Valid @RequestBody CrearNivelEducativoDto dto) {
        NivelEducativoDto creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar nivel educativo por ID", description = "Obtiene los detalles de un nivel educativo específico")
    public ResponseEntity<NivelEducativoDto> buscarPorId(
            @Parameter(description = "ID del nivel educativo") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar nivel educativo por código", description = "Obtiene un nivel educativo por su código único")
    public ResponseEntity<NivelEducativoDto> buscarPorCodigo(
            @Parameter(description = "Código del nivel educativo") @PathVariable String codigo) {
        return service.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los niveles educativos", description = "Obtiene una lista de todos los niveles educativos registrados")
    public ResponseEntity<List<NivelEducativoDto>> listarTodos() {
        List<NivelEducativoDto> niveles = service.listarTodos();
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar niveles por categoría", description = "Obtiene niveles educativos filtrados por categoría (PREESCOLAR, BASICA, MEDIA, SUPERIOR)")
    public ResponseEntity<List<NivelEducativoDto>> listarPorCategoria(
            @Parameter(description = "Categoría del nivel educativo") @PathVariable String categoria) {
        List<NivelEducativoDto> niveles = service.listarPorCategoria(categoria);
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/nivel-superior/{nivelSuperiorId}")
    @Operation(summary = "Listar niveles por nivel superior", description = "Obtiene niveles educativos que dependen de un nivel superior específico")
    public ResponseEntity<List<NivelEducativoDto>> listarPorNivelSuperior(
            @Parameter(description = "ID del nivel superior") @PathVariable Long nivelSuperiorId) {
        List<NivelEducativoDto> niveles = service.listarPorNivelSuperior(nivelSuperiorId);
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar niveles activos", description = "Obtiene solo los niveles educativos que están activos")
    public ResponseEntity<List<NivelEducativoDto>> listarActivos() {
        List<NivelEducativoDto> niveles = service.listarActivos();
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/categoria-activos/{categoria}")
    @Operation(summary = "Listar niveles activos por categoría", description = "Obtiene niveles educativos activos filtrados por categoría")
    public ResponseEntity<List<NivelEducativoDto>> listarPorCategoriaActivos(
            @Parameter(description = "Categoría del nivel educativo") @PathVariable String categoria) {
        List<NivelEducativoDto> niveles = service.listarPorCategoriaActivos(categoria);
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/principales")
    @Operation(summary = "Listar niveles principales", description = "Obtiene los niveles educativos principales (sin nivel superior)")
    public ResponseEntity<List<NivelEducativoDto>> listarNivelesPrincipales() {
        List<NivelEducativoDto> niveles = service.listarNivelesPrincipales();
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas completas de los niveles educativos")
    public ResponseEntity<NivelEducativoEstadisticasDto> obtenerEstadisticas() {
        NivelEducativoEstadisticasDto estadisticas = service.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar nivel educativo", description = "Actualiza los datos de un nivel educativo existente")
    public ResponseEntity<NivelEducativoDto> actualizar(
            @Parameter(description = "ID del nivel educativo") @PathVariable Long id,
            @Valid @RequestBody ActualizarNivelEducativoDto dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar nivel educativo", description = "Elimina un nivel educativo si no tiene dependencias")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del nivel educativo") @PathVariable Long id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
