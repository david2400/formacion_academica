package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.CambiarEstadoInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ListarInscripcionesUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.RegistrarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admisiones/inscripciones")
public class InscripcionController {

    private final RegistrarInscripcionUseCase registrarUseCase;
    private final ConsultarInscripcionUseCase consultarUseCase;
    private final ListarInscripcionesUseCase listarUseCase;
    private final CambiarEstadoInscripcionUseCase cambiarEstadoUseCase;

    public InscripcionController(RegistrarInscripcionUseCase registrarUseCase,
                                 ConsultarInscripcionUseCase consultarUseCase,
                                 ListarInscripcionesUseCase listarUseCase,
                                 CambiarEstadoInscripcionUseCase cambiarEstadoUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.cambiarEstadoUseCase = cambiarEstadoUseCase;
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
        ActualizarEstadoInscripcionDto payload = new ActualizarEstadoInscripcionDto(
                inscripcionId,
                request.nuevoEstado(),
                request.motivo()
        );
        return ResponseEntity.ok(cambiarEstadoUseCase.cambiarEstado(payload));
    }
}
