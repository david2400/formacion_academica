package com.kleverkids.formacion_academica.modules.estados.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estados.application.input.catalogo.AdministrarCatalogoEstadosUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.contexto.ConsultarEstadoContextoUseCase;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.ActualizarParametrizacionDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.CrearEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.HabilitarEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.RegistrarContextoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.model.ContextoEstado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.Estado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Catálogo de estados: administración y consulta.
 *
 * <p>Separa <b>qué estados existen</b> (catálogo, reutilizable) de <b>dónde aplica
 * cada uno</b> (parametrización por contexto). Así {@code activo} es una sola fila
 * compartida por matrícula, grupo, inscripción y asignación estudiante-grupo, y
 * cada contexto define su propio inicial y sus propios terminales.
 *
 * <p>Todo vive en esta aplicación: no hay dependencia de ningún servicio externo.
 *
 * <p>La ruta es /catalogo-estados y no /estados porque el módulo legado de este
 * mismo proyecto (EstadoController, con el diseño anterior basado en id_modulo)
 * todavía ocupa /estados. Ese módulo es andamio no funcional y debería borrarse;
 * cuando se haga, esta ruta puede simplificarse.
 */
@Tag(name = "Estados", description = "Catálogo de estados y su parametrización por contexto")
@RestController
@RequestMapping("/catalogo-estados")
@RequiredArgsConstructor
public class CatalogoEstadosController {

    private final AdministrarCatalogoEstadosUseCase administrar;
    private final ConsultarEstadoContextoUseCase consultar;

    // ── Catálogo ────────────────────────────────────────────────────────────

    @Operation(summary = "Listar catálogo", description = "Todos los estados disponibles, sin filtrar por contexto")
    @GetMapping
    public ResponseEntity<List<Estado>> listarEstados() {
        return ResponseEntity.ok(administrar.listarEstados());
    }

    @Operation(summary = "Consultar por código", description = "El código es el identificador estable entre entornos")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Estado> consultarPorCodigo(@PathVariable String codigo) {
        return administrar.consultarEstadoPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear estado")
    @PostMapping
    public ResponseEntity<Estado> crearEstado(@Valid @RequestBody CrearEstadoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administrar.crearEstado(request));
    }

    @Operation(summary = "Actualizar estado")
    @PutMapping("/{id}")
    public ResponseEntity<Estado> actualizarEstado(@PathVariable Long id,
            @Valid @RequestBody CrearEstadoDto request) {
        return ResponseEntity.ok(administrar.actualizarEstado(id, request));
    }

    @Operation(summary = "Eliminar estado", description = "Se rechaza si el estado sigue habilitado en algún contexto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Long id) {
        administrar.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }

    // ── Contextos ───────────────────────────────────────────────────────────

    @Operation(summary = "Listar contextos", description = "Tipos de entidad que usan estados")
    @GetMapping("/contextos")
    public ResponseEntity<List<ContextoEstado>> listarContextos() {
        return ResponseEntity.ok(administrar.listarContextos());
    }

    @Operation(summary = "Registrar contexto", description = "Declara un contexto (módulo + entidad). "
            + "Es idempotente: registrar la misma pareja devuelve el contexto existente.")
    @PostMapping("/contextos")
    public ResponseEntity<ContextoEstado> registrarContexto(@Valid @RequestBody RegistrarContextoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administrar.registrarContexto(request));
    }

    @Operation(summary = "Estados de un contexto", description = "El endpoint que consume el frontend. Devuelve el "
            + "catálogo aplicable al contexto, ordenado. Los clientes construyen filtros y selectores a partir de "
            + "esta respuesta y razonan por el campo codigo, nunca por estado_id.")
    @GetMapping("/contextos/{codigo}/estados")
    public ResponseEntity<List<EstadoContexto>> listarEstadosDeContexto(
            @PathVariable String codigo,
            @RequestParam(name = "id_empresa", required = false) Long idEmpresa) {
        return ResponseEntity.ok(consultar.listarPorContexto(codigo, idEmpresa));
    }

    @Operation(summary = "Estado inicial del contexto", description = "El que se asigna al crear una entidad de este contexto")
    @GetMapping("/contextos/{codigo}/estados/inicial")
    public ResponseEntity<EstadoContexto> obtenerInicial(
            @PathVariable String codigo,
            @RequestParam(name = "id_empresa", required = false) Long idEmpresa) {
        return consultar.obtenerInicial(codigo, idEmpresa)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Habilitar un estado en el contexto")
    @PostMapping("/contextos/{codigo}/estados")
    public ResponseEntity<EstadoContexto> habilitar(@PathVariable String codigo,
            @Valid @RequestBody HabilitarEstadoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administrar.habilitarEstado(codigo, request));
    }

    @Operation(summary = "Ajustar la parametrización", description = "Cambia si es inicial, final o su orden")
    @PutMapping("/contextos/estados/{id}")
    public ResponseEntity<EstadoContexto> actualizarParametrizacion(@PathVariable Long id,
            @Valid @RequestBody ActualizarParametrizacionDto request) {
        return ResponseEntity.ok(administrar.actualizarParametrizacion(id, request));
    }

    @Operation(summary = "Quitar un estado del contexto")
    @DeleteMapping("/contextos/estados/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable Long id) {
        administrar.deshabilitarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
