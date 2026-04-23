package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asignacion_examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Tag(name = "Asignaciones de Exámenes", description = "Gestiona la asignación de exámenes a clases/grupos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/asignaciones-examenes")
public class AsignacionExamenController {
    
    private final CrearAsignacionExamenUseCase crearUseCase;
    private final ActualizarAsignacionExamenUseCase actualizarUseCase;
    private final ConsultarAsignacionExamenUseCase consultarUseCase;
    private final BuscarAsignacionesExamenUseCase buscarUseCase;
    private final EliminarAsignacionExamenUseCase eliminarUseCase;
    private final CambiarEstadoAsignacionUseCase cambiarEstadoUseCase;
    
    @PostMapping
    @Operation(
        summary = "Crear asignación de examen",
        description = "Asigna un examen configurado a una clase/grupo con fecha y configuración específica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Asignación creada exitosamente",
            content = @Content(schema = @Schema(implementation = AsignacionExamenDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o asignación duplicada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Examen o clase no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<AsignacionExamenDto> crear(@Valid @RequestBody CrearAsignacionExamenDto dto) {
        log.info("Creando asignación de examen {} para clase {}", dto.examenId(), dto.claseId());
        AsignacionExamenDto resultado = crearUseCase.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar asignación de examen",
        description = "Actualiza la configuración de una asignación existente"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Asignación actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = AsignacionExamenDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Asignación no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<AsignacionExamenDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarAsignacionExamenDto dto) {
        log.info("Actualizando asignación de examen ID: {}", id);
        AsignacionExamenDto resultado = actualizarUseCase.actualizar(id, dto);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Consultar asignación de examen",
        description = "Obtiene los detalles de una asignación específica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Asignación encontrada",
            content = @Content(schema = @Schema(implementation = AsignacionExamenDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Asignación no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<AsignacionExamenDto> consultar(@PathVariable Long id) {
        log.info("Consultando asignación de examen ID: {}", id);
        AsignacionExamenDto resultado = consultarUseCase.consultarPorId(id);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping
    @Operation(
        summary = "Listar asignaciones de exámenes",
        description = """
            Busca asignaciones aplicando filtros opcionales con paginación:
            - examenId: Filtra por examen específico
            - claseId: Filtra por clase específica
            - grado: Filtra por grado
            - grupo: Filtra por grupo
            - estado: Filtra por estado (PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA)
            - fechaDesde: Filtra asignaciones desde esta fecha
            - fechaHasta: Filtra asignaciones hasta esta fecha
            - activas: Solo asignaciones activas actualmente (default: true)
            - page: Número de página (default: 0)
            - size: Tamaño de página (default: 20)
            - sort: Campo de ordenamiento (ej: fechaInicio,asc)
            """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de asignaciones encontradas",
            content = @Content(schema = @Schema(implementation = Page.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parámetros de búsqueda inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Page<AsignacionExamenDto>> listar(
            @RequestParam(required = false) Long examenId,
            @RequestParam(required = false) Long claseId,
            @RequestParam(required = false) String grado,
            @RequestParam(required = false) String grupo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fechaDesde,
            @RequestParam(required = false) LocalDate fechaHasta,
            @RequestParam(required = false, defaultValue = "true") Boolean activas,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "fechaInicio,asc") String[] sort) {
        
        log.info("Listando asignaciones - examenId: {}, claseId: {}, estado: {}, activas: {}", 
            examenId, claseId, estado, activas);
        
        BuscarAsignacionesDto criterios = new BuscarAsignacionesDto(
            examenId,
            claseId,
            grado,
            grupo,
            estado,
            fechaDesde,
            fechaHasta,
            activas
        );
        
        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("desc")
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        
        Page<AsignacionExamenDto> resultado = buscarUseCase.buscar(criterios, pageable);
        
        log.info("Asignaciones encontradas: {} de {} totales", 
            resultado.getNumberOfElements(), resultado.getTotalElements());
        
        return ResponseEntity.ok(resultado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar asignación de examen",
        description = "Elimina una asignación de examen"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Asignación eliminada exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Asignación no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando asignación de examen ID: {}", id);
        eliminarUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/estado")
    @Operation(
        summary = "Cambiar estado de asignación",
        description = "Cambia el estado de una asignación (PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Estado cambiado exitosamente",
            content = @Content(schema = @Schema(implementation = AsignacionExamenDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Estado inválido",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Asignación no encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<AsignacionExamenDto> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        log.info("Cambiando estado de asignación {} a {}", id, estado);
        AsignacionExamenDto resultado = cambiarEstadoUseCase.cambiarEstado(id, estado);
        return ResponseEntity.ok(resultado);
    }
}
