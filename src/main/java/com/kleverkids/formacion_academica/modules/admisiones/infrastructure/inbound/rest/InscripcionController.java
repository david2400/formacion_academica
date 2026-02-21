package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.CambiarEstadoInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.EliminarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ListarInscripcionesUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.RegistrarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona las incripciones de los estudiantes")
@Tag(name = "Inscripciones")
@RestController
@RequestMapping("/admisiones/inscripciones")
public class InscripcionController {

    private final RegistrarInscripcionUseCase registrarUseCase;
    private final ConsultarInscripcionUseCase consultarUseCase;
    private final ListarInscripcionesUseCase listarUseCase;
    private final CambiarEstadoInscripcionUseCase cambiarEstadoUseCase;
    private final EliminarInscripcionUseCase eliminarUseCase;

    public InscripcionController(RegistrarInscripcionUseCase registrarUseCase,
                                 ConsultarInscripcionUseCase consultarUseCase,
                                 ListarInscripcionesUseCase listarUseCase,
                                 CambiarEstadoInscripcionUseCase cambiarEstadoUseCase,
                                 EliminarInscripcionUseCase eliminarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.cambiarEstadoUseCase = cambiarEstadoUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping
    public ResponseEntity<InscripcionDto> registrar(@RequestBody CrearInscripcionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @GetMapping("/{inscripcionId}")
    public ResponseEntity<InscripcionDto> consultar(@PathVariable UUID inscripcionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(inscripcionId));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionDto>> listar(@RequestBody(required = false) ListarInscripcionesFiltroDto filtro) {
        return ResponseEntity.ok(listarUseCase.listar(filtro));
    }

    @PutMapping("/{inscripcionId}/estado")
    public ResponseEntity<InscripcionDto> cambiarEstado(@PathVariable UUID inscripcionId,
                                                        @RequestBody ActualizarEstadoInscripcionDto request) {
        request.setInscripcionId(inscripcionId);
        return ResponseEntity.ok(cambiarEstadoUseCase.cambiarEstado(request));
    }

    @DeleteMapping("/{inscripcionId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID inscripcionId) {
        eliminarUseCase.eliminar(inscripcionId);
        return ResponseEntity.noContent().build();
    }
}
