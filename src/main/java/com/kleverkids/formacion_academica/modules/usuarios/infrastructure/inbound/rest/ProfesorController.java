package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Profesores del instituto. Todas las operaciones van acotadas a una empresa
 * ({@code empresaId}), y la identidad de cada profesor se lee de access_control.
 */
@Tag(name = "Usuarios · Profesores", description = "Vinculación de usuarios de access_control como profesores")
@RestController
@RequestMapping("/usuarios/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private static final int MAX_PAGINA = 100;

    private final CrearProfesorUseCase crear;
    private final ActualizarProfesorUseCase actualizar;
    private final CambiarEstadoProfesorUseCase cambiarEstado;
    private final EliminarProfesorUseCase eliminar;
    private final ConsultarProfesorUseCase consultar;
    private final ListarProfesoresUseCase listar;
    private final BuscarUsuariosDisponiblesUseCase disponibles;
    private final ResumirProfesoresUseCase resumir;
    private final EmpresaAcceso empresaAcceso;

    @Operation(summary = "Listar profesores", description = "Paginado; texto busca en nombre, usuario y documento. sort=nombre|fechaVinculacion|createdAt")
    @GetMapping
    public ResponseEntity<Page<ProfesorDto>> listar(@RequestParam Long empresaId,
                                                    @RequestParam(required = false) String texto,
                                                    @RequestParam(required = false) Boolean activo,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size,
                                                    @RequestParam(defaultValue = "nombre") String sort,
                                                    @RequestParam(defaultValue = "asc") String direction) {
        empresaAcceso.verificar(empresaId);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAX_PAGINA),
                Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        FiltroProfesoresDto filtro = FiltroProfesoresDto.builder().empresaId(empresaId).texto(texto).activo(activo).build();
        return ResponseEntity.ok(listar.listar(filtro, pageable));
    }

    @Operation(summary = "Usuarios de la empresa que aún no son profesores", description = "Para el buscador del alta")
    @GetMapping("/usuarios-disponibles")
    public ResponseEntity<List<UsuarioDisponibleDto>> usuariosDisponibles(@RequestParam Long empresaId,
                                                                          @RequestParam(required = false) String texto,
                                                                          @RequestParam(defaultValue = "20") int limite) {
        empresaAcceso.verificar(empresaId);
        return ResponseEntity.ok(disponibles.buscarDisponibles(empresaId, texto, limite));
    }

    @Operation(summary = "Nombres de profesores por usuarioId", description = "Lote para mostrar docentes en clases, feedback y resultados")
    @GetMapping("/resumen")
    public ResponseEntity<List<ProfesorResumenDto>> resumen(@RequestParam Long empresaId, @RequestParam List<Long> usuarioIds) {
        empresaAcceso.verificar(empresaId);
        return ResponseEntity.ok(resumir.resumir(empresaId, usuarioIds));
    }

    @Operation(summary = "Detalle de un profesor")
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDto> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(propio(id));
    }

    @Operation(summary = "Registrar un usuario como profesor", description = "409 si ya lo es; 400 si el usuario no pertenece a la empresa")
    @PostMapping
    public ResponseEntity<ProfesorDto> crear(@Valid @RequestBody CrearProfesorDto dto) {
        empresaAcceso.verificar(dto.getEmpresaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(crear.crear(dto));
    }

    @Operation(summary = "Actualizar datos del profesor")
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDto> actualizar(@PathVariable Long id, @Valid @RequestBody ActualizarProfesorDto dto) {
        propio(id);
        return ResponseEntity.ok(actualizar.actualizar(id, dto));
    }

    @Operation(summary = "Activar o desactivar un profesor")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProfesorDto> cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambiarEstadoProfesorDto dto) {
        propio(id);
        return ResponseEntity.ok(cambiarEstado.cambiarEstado(id, dto.getActivo()));
    }

    @Operation(summary = "Eliminar un profesor", description = "Borrado lógico; volver a registrarlo lo recupera")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        propio(id);
        eliminar.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /** Carga el profesor y verifica que sea de una empresa del solicitante. */
    private ProfesorDto propio(Long id) {
        ProfesorDto profesor = consultar.consultar(id);
        empresaAcceso.verificar(profesor.getEmpresaId());
        return profesor;
    }
}
