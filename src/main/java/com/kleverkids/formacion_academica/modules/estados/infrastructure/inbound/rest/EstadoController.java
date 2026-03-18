package com.kleverkids.formacion_academica.modules.estados.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estados.application.input.estado.ActualizarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.ConsultarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.CrearEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.EliminarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.consulta.ConsultaEspecializadaUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.entidad.GestionarEntidadEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.transicion.ConsultarTransicionUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.transicion.ValidarTransicionUseCase;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.CambioEstadoRequest;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/estados")
@RequiredArgsConstructor
@Tag(name = "Gestión Centralizada de Estados", description = "API para gestión de estados centralizados")
public class EstadoController {

    // Use Cases para gestión de estados
    private final CrearEstadoUseCase crearEstadoUseCase;
    private final ConsultarEstadoUseCase consultarEstadoUseCase;
    private final ActualizarEstadoUseCase actualizarEstadoUseCase;
    private final EliminarEstadoUseCase eliminarEstadoUseCase;
    
    // Use Cases para gestión de transiciones
    private final ValidarTransicionUseCase validarTransicionUseCase;
    private final ConsultarTransicionUseCase consultarTransicionUseCase;
    
    // Use Cases para gestión de entidades
    private final GestionarEntidadEstadoUseCase gestionarEntidadEstadoUseCase;
    
    // Use Cases para consultas especializadas
    private final ConsultaEspecializadaUseCase consultaEspecializadaUseCase;

    // ========================================
    // CONSULTAS DE ESTADOS
    // ========================================

    @GetMapping("/modulo/{idModulo}")
    @Operation(summary = "Listar estados por módulo", description = "Obtiene todos los estados activos de un módulo específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados encontrados"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarEstadosPorModulo(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoDto> estados = consultarEstadoUseCase.listarPorModulo(idModulo);
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/modulo/{idModulo}/empresa/{idEmpresa}")
    @Operation(summary = "Listar estados por módulo y empresa", description = "Obtiene estados de un módulo específicos para una empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados encontrados"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarEstadosPorModuloYEmpresa(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo,
            @Parameter(description = "ID de la empresa") @PathVariable Long idEmpresa) {
        List<EstadoDto> estados = consultarEstadoUseCase.listarPorModuloYEmpresa(idModulo, idEmpresa);
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/modulo/{idModulo}/iniciales")
    @Operation(summary = "Listar estados iniciales", description = "Obtiene los estados iniciales configurados para un módulo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados iniciales encontrados"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarEstadosIniciales(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoDto> estados = consultarEstadoUseCase.listarIniciales(idModulo);
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/modulo/{idModulo}/finales")
    @Operation(summary = "Listar estados finales", description = "Obtiene los estados finales configurados para un módulo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados finales encontrados"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarEstadosFinales(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoDto> estados = consultarEstadoUseCase.listarFinales(idModulo);
        return ResponseEntity.ok(estados);
    }

    // ========================================
    // CONSULTAS DE TRANSICIONES
    // ========================================

    @GetMapping("/transiciones/modulo/{idModulo}")
    @Operation(summary = "Listar transiciones por módulo", description = "Obtiene todas las transiciones activas de un módulo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transiciones encontradas"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoTransicionEntity>> listarTransicionesPorModulo(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoTransicionEntity> transiciones = consultarTransicionUseCase.listarPorModulo(idModulo);
        return ResponseEntity.ok(transiciones);
    }

    @GetMapping("/transiciones/desde/{estadoOrigenId}/modulo/{idModulo}")
    @Operation(summary = "Listar transiciones desde un estado", description = "Obtiene las transiciones posibles desde un estado específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transiciones encontradas"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoTransicionEntity>> listarTransicionesDesdeEstado(
            @Parameter(description = "ID del estado origen") @PathVariable Long estadoOrigenId,
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoTransicionEntity> transiciones = consultarTransicionUseCase.listarDesdeEstado(estadoOrigenId, idModulo);
        return ResponseEntity.ok(transiciones);
    }

    @GetMapping("/transiciones/validar")
    @Operation(summary = "Validar transición", description = "Verifica si una transición entre estados es válida")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validación completada"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Boolean> validarTransicion(
            @Parameter(description = "ID del estado origen") @RequestParam Long estadoOrigenId,
            @Parameter(description = "ID del estado destino") @RequestParam Long estadoDestinoId,
            @Parameter(description = "ID del módulo") @RequestParam Long idModulo,
            @Parameter(description = "ID de la empresa (opcional)") @RequestParam(required = false) Long idEmpresa) {
        
        boolean isValida;
        if (idEmpresa != null) {
            isValida = validarTransicionUseCase.esValida(estadoOrigenId, estadoDestinoId, idModulo, idEmpresa);
        } else {
            isValida = validarTransicionUseCase.esValida(estadoOrigenId, estadoDestinoId, idModulo);
        }
        
        return ResponseEntity.ok(isValida);
    }

    // ========================================
    // GESTIÓN DE ESTADOS
    // ========================================

    @PostMapping
    @Operation(summary = "Crear estado", description = "Crea un nuevo estado en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<EstadoDto> crearEstado(@Valid @RequestBody EstadoDto estado) {
        EstadoDto creado = crearEstadoUseCase.crear(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estado por ID", description = "Obtiene un estado específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<EstadoDto> obtenerEstadoPorId(
            @Parameter(description = "ID del estado") @PathVariable Long id) {
        return consultarEstadoUseCase.consultarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener estado por código", description = "Obtiene un estado específico por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<EstadoDto> obtenerEstadoPorCodigo(
            @Parameter(description = "Código del estado") @PathVariable String codigo) {
        return consultarEstadoUseCase.consultarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado", description = "Actualiza un estado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<EstadoDto> actualizarEstado(
            @Parameter(description = "ID del estado") @PathVariable Long id,
            @Valid @RequestBody EstadoDto estado) {
        return actualizarEstadoUseCase.actualizar(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado", description = "Elimina un estado del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado eliminado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Void> eliminarEstado(
            @Parameter(description = "ID del estado") @PathVariable Long id) {
        if (eliminarEstadoUseCase.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ========================================
    // VALIDACIÓN DE TRANSICIONES
    // ========================================

    @GetMapping("/transicion/validar/{origen}/{destino}/{modulo}")
    @Operation(summary = "Validar transición por path", description = "Verifica si una transición entre estados es válida usando parámetros de path")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validación completada"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Boolean> validarTransicionPorPath(
            @Parameter(description = "ID estado origen") @PathVariable Long origen,
            @Parameter(description = "ID estado destino") @PathVariable Long destino,
            @Parameter(description = "ID del módulo") @PathVariable Long modulo) {
        boolean esValida = validarTransicionUseCase.esValida(origen, destino, modulo);
        return ResponseEntity.ok(esValida);
    }

    // ========================================
    // GESTIÓN DE ESTADOS DE ENTIDADES
    // ========================================

    @PostMapping("/asignar-inicial")
    @Operation(summary = "Asignar estado inicial", description = "Asigna el estado inicial a una entidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado inicial asignado"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Void> asignarEstadoInicial(
            @Parameter(description = "Tipo de entidad") @RequestParam String entidadTipo,
            @Parameter(description = "ID de entidad") @RequestParam Long entidadId,
            @Parameter(description = "ID del módulo") @RequestParam Long idModulo,
            @Parameter(description = "ID del usuario que realiza el cambio") @RequestParam Long idUsuarioCambio) {
        gestionarEntidadEstadoUseCase.asignarEstadoInicial(entidadTipo, entidadId, idModulo, idUsuarioCambio);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cambiar-estado")
    @Operation(summary = "Cambiar estado", description = "Realiza un cambio de estado con validación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cambio de estado realizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o transición no permitida"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Void> cambiarEstado(@Valid @RequestBody CambioEstadoRequest request) {
        gestionarEntidadEstadoUseCase.cambiarEstadoConValidacion(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estado-actual/{entidadTipo}/{entidadId}")
    @Operation(summary = "Obtener estado actual", description = "Obtiene el estado actual de una entidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actual encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<EstadoDto> obtenerEstadoActual(
            @Parameter(description = "Tipo de entidad") @PathVariable String entidadTipo,
            @Parameter(description = "ID de entidad") @PathVariable Long entidadId) {
        return gestionarEntidadEstadoUseCase.obtenerEstadoActual(entidadTipo, entidadId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/historial/{entidadTipo}/{entidadId}")
    @Operation(summary = "Listar historial", description = "Obtiene el historial de cambios de estado de una entidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarHistorialEstados(
            @Parameter(description = "Tipo de entidad") @PathVariable String entidadTipo,
            @Parameter(description = "ID de entidad") @PathVariable Long entidadId) {
        List<EstadoDto> historial = gestionarEntidadEstadoUseCase.listarHistorialEstados(entidadTipo, entidadId);
        return ResponseEntity.ok(historial);
    }

    // ========================================
    // CONSULTAS ESPECIALIZADAS
    // ========================================

    @GetMapping("/con-estado/{estadoId}")
    @Operation(summary = "Listar entidades con estado", description = "Obtiene todas las entidades que tienen un estado específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entidades encontradas"),
            @ApiResponse(responseCode = "400", description = "ID de estado inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> listarEntidadesConEstado(
            @Parameter(description = "ID del estado") @PathVariable Long estadoId) {
        List<EstadoDto> entidades = consultaEspecializadaUseCase.listarEntidadesConEstado(estadoId);
        return ResponseEntity.ok(entidades);
    }

    @GetMapping("/workflow/{idModulo}")
    @Operation(summary = "Obtener workflow completo", description = "Obtiene todos los estados de un módulo en orden de workflow")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workflow encontrado"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EstadoDto>> obtenerWorkflowCompleto(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        List<EstadoDto> workflow = consultaEspecializadaUseCase.obtenerWorkflowCompleto(idModulo);
        return ResponseEntity.ok(workflow);
    }

    @GetMapping("/entidades/{entidadTipo}/modulo/{idModulo}")
    @Operation(summary = "Listar entidades por módulo", description = "Obtiene entidades de un tipo específico filtradas por módulo de estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entidades encontradas"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<List<EntidadEstadoEntity>> listarEntidadesPorModulo(
            @Parameter(description = "Tipo de entidad") @PathVariable String entidadTipo,
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        
        List<EntidadEstadoEntity> entidades = consultaEspecializadaUseCase.listarEntidadesPorModulo(entidadTipo, idModulo);
        return ResponseEntity.ok(entidades);
    }

    @GetMapping("/modulo/{idModulo}/configurado")
    @Operation(summary = "Verificar configuración", description = "Verifica si un módulo tiene estados configurados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificación completada"),
            @ApiResponse(responseCode = "400", description = "ID de módulo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<Boolean> tieneEstadosConfigurados(
            @Parameter(description = "ID del módulo") @PathVariable Long idModulo) {
        
        boolean configurado = consultaEspecializadaUseCase.tieneEstadosConfigurados(idModulo);
        return ResponseEntity.ok(configurado);
    }

}
