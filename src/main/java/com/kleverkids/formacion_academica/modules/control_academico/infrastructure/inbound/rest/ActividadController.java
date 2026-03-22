package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.actividad.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.ProgresoActividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/actividades")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Actividades", description = "Gestión de actividades académicas")
public class ActividadController {

    private final CrearActividadUseCase crearActividadUseCase;
    private final ActualizarActividadUseCase actualizarActividadUseCase;
    private final ConsultarActividadUseCase consultarActividadUseCase;
    private final GestionarProgresoActividadUseCase gestionarProgresoActividadUseCase;

    // ==================== OPERACIONES CRUD ====================

    @Operation(summary = "Crear nueva actividad", description = "Crea una nueva actividad académica en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Actividad creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Actividad.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "No autorizado para crear actividades", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Actividad> crear(@Valid @RequestBody CrearActividadDto request) {
        log.info("Creando nueva actividad: {} - Tipo: {}", request.getTitulo(), request.getTipo());
        
        try {
            Actividad actividad = crearActividadUseCase.crear(request);
            log.info("Actividad creada exitosamente con ID: {}", actividad.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(actividad);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al crear actividad: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear actividad", e);
            throw new RuntimeException("Error al crear actividad", e);
        }
    }

    @Operation(summary = "Actualizar actividad existente", description = "Actualiza los datos de una actividad existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividad actualizada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Actividad.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content),
        @ApiResponse(responseCode = "403", description = "No autorizado para actualizar actividades", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{actividadId}")
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Actividad> actualizar(
            @Parameter(description = "ID de la actividad a actualizar") @PathVariable Long actividadId,
            @Valid @RequestBody ActualizarActividadDto request) {
        
        log.info("Actualizando actividad ID: {}", actividadId);
        request.setId(actividadId);
        
        try {
            Actividad actividad = actualizarActividadUseCase.actualizar(request);
            log.info("Actividad actualizada exitosamente: {}", actividad.getId());
            return ResponseEntity.ok(actividad);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al actualizar actividad: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar actividad", e);
            throw new RuntimeException("Error al actualizar actividad", e);
        }
    }

    @Operation(summary = "Consultar actividad por ID", description = "Obtiene los detalles de una actividad específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividad encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Actividad.class))),
        @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{actividadId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Actividad> consultar(
            @Parameter(description = "ID de la actividad a consultar") @PathVariable Long actividadId) {
        
        log.debug("Consultando actividad ID: {}", actividadId);
        
        Optional<Actividad> actividad = consultarActividadUseCase.consultarPorId(actividadId);
        
        return actividad.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todas las actividades", description = "Obtiene un listado de todas las actividades del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de actividades obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Actividad.class)))),
        @ApiResponse(responseCode = "403", description = "No autorizado para listar actividades", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<Actividad>> listar() {
        log.debug("Listando todas las actividades");
        
        List<Actividad> actividades = consultarActividadUseCase.listar();
        return ResponseEntity.ok(actividades);
    }

    // ==================== BÚSQUEDAS ESPECIALIZADAS ====================

    @Operation(summary = "Buscar actividades por curso", description = "Obtiene todas las actividades pertenecientes a un curso específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividades del curso obtenidas exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Actividad.class)))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/curso/{cursoId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<Actividad>> buscarPorCurso(
            @Parameter(description = "ID del curso") @PathVariable Long cursoId) {
        
        log.debug("Buscando actividades por curso ID: {}", cursoId);
        
        List<Actividad> actividades = consultarActividadUseCase.buscarPorCurso(cursoId);
        return ResponseEntity.ok(actividades);
    }

    @Operation(summary = "Buscar actividades por módulo", description = "Obtiene todas las actividades pertenecientes a un módulo específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividades del módulo obtenidas exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Actividad.class)))),
        @ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/modulo/{moduloId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<Actividad>> buscarPorModulo(
            @Parameter(description = "ID del módulo") @PathVariable Long moduloId) {
        
        log.debug("Buscando actividades por módulo ID: {}", moduloId);
        
        List<Actividad> actividades = consultarActividadUseCase.buscarPorModulo(moduloId);
        return ResponseEntity.ok(actividades);
    }

    @Operation(summary = "Buscar actividades con filtros", description = "Busca actividades aplicando múltiples filtros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividades encontradas",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Actividad.class)))),
        @ApiResponse(responseCode = "400", description = "Parámetros de filtro inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/buscar")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<Actividad>> buscarConFiltro(@Valid @RequestBody FiltroActividadDto filtro) {
        log.debug("Buscando actividades con filtros: {}", filtro);
        
        List<Actividad> actividades = consultarActividadUseCase.buscarConFiltro(filtro);
        return ResponseEntity.ok(actividades);
    }

    @Operation(summary = "Obtener actividades disponibles para estudiante", description = "Obtiene las actividades que un estudiante puede realizar actualmente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actividades disponibles obtenidas",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Actividad.class)))),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/disponibles/estudiante/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<Actividad>> obtenerActividadesDisponibles(
            @Parameter(description = "ID del estudiante") @PathVariable Long estudianteId) {
        
        log.debug("Obteniendo actividades disponibles para estudiante ID: {}", estudianteId);
        
        List<Actividad> actividades = consultarActividadUseCase.buscarActividadesDisponibles(estudianteId);
        return ResponseEntity.ok(actividades);
    }

    // ==================== GESTIÓN DE PROGRESO ====================

    @Operation(summary = "Registrar inicio de actividad", description = "Registra que un estudiante ha comenzado una actividad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inicio registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "No se puede iniciar la actividad", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad o estudiante no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/{actividadId}/iniciar/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Void> registrarInicio(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "ID del estudiante") @PathVariable Long estudianteId) {
        
        log.info("Registrando inicio de actividad ID: {} para estudiante ID: {}", actividadId, estudianteId);
        
        gestionarProgresoActividadUseCase.registrarInicio(actividadId, estudianteId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Registrar completado de actividad", description = "Registra que un estudiante ha completado una actividad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Completado registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de completado inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad o estudiante no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/{actividadId}/completar/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Void> registrarCompletado(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "ID del estudiante") @PathVariable Long estudianteId,
            @Valid @RequestBody RegistrarProgresoDto resultado) {
        
        log.info("Registrando completado de actividad ID: {} para estudiante ID: {}", actividadId, estudianteId);
        
        gestionarProgresoActividadUseCase.registrarCompletado(actividadId, estudianteId, resultado);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar progreso de actividad", description = "Actualiza el progreso parcial de un estudiante en una actividad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Progreso actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de progreso inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad o estudiante no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{actividadId}/progreso/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Void> actualizarProgreso(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "ID del estudiante") @PathVariable Long estudianteId,
            @Valid @RequestBody ActualizarProgresoDto progreso) {
        
        log.debug("Actualizando progreso de actividad ID: {} para estudiante ID: {}", actividadId, estudianteId);
        
        gestionarProgresoActividadUseCase.actualizarProgreso(actividadId, estudianteId, progreso);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener progreso de actividad", description = "Obtiene el progreso general de una actividad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Progreso obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProgresoActividad.class))),
        @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{actividadId}/progreso")
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<ProgresoActividad> obtenerProgreso(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId) {
        
        log.debug("Obteniendo progreso de actividad ID: {}", actividadId);
        
        ProgresoActividad progreso = gestionarProgresoActividadUseCase.obtenerProgreso(actividadId);
        return ResponseEntity.ok(progreso);
    }

    @Operation(summary = "Obtener progreso de estudiante en actividad", description = "Obtiene el progreso específico de un estudiante en una actividad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Progreso del estudiante obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProgresoActividad.class))),
        @ApiResponse(responseCode = "404", description = "Actividad o estudiante no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{actividadId}/progreso/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<ProgresoActividad> obtenerProgresoEstudiante(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "ID del estudiante") @PathVariable Long estudianteId) {
        
        log.debug("Obteniendo progreso de estudiante ID: {} en actividad ID: {}", estudianteId, actividadId);
        
        ProgresoActividad progreso = gestionarProgresoActividadUseCase.obtenerProgresoEstudiante(actividadId, estudianteId);
        return ResponseEntity.ok(progreso);
    }

    // ==================== ENDPOINTS DE GESTIÓN AVANZADA ====================

    @Operation(summary = "Actualizar estado de actividad", description = "Cambia el estado de una actividad (BORRADOR, ACTIVA, PAUSADA, etc.)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Transición de estado no permitida", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content),
        @ApiResponse(responseCode = "403", description = "No autorizado para cambiar estado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{actividadId}/estado")
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Void> actualizarEstado(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "Nuevo estado de la actividad") @RequestParam String nuevoEstado) {
        
        log.info("Actualizando estado de actividad ID: {} a: {}", actividadId, nuevoEstado);
        
        // Este endpoint requeriría un caso de uso adicional para manejar cambios de estado
        // Por ahora, se deja como placeholder
        
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reordenar actividades", description = "Actualiza el orden de las actividades dentro de un curso o módulo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de ordenamiento inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content),
        @ApiResponse(responseCode = "403", description = "No autorizado para reordenar", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{actividadId}/orden")
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMINISTRADOR')")
    public ResponseEntity<Void> actualizarOrden(
            @Parameter(description = "ID de la actividad") @PathVariable Long actividadId,
            @Parameter(description = "Nuevo orden") @RequestParam Integer nuevoOrden) {
        
        log.info("Actualizando orden de actividad ID: {} a: {}", actividadId, nuevoOrden);
        
        // Este endpoint requeriría un caso de uso adicional para manejar reordenamiento
        // Por ahora, se deja como placeholder
        
        return ResponseEntity.ok().build();
    }
}
