package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;
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
@RequestMapping("/api/control-academico/examenes/{examenId}/tematicas")
public class TematicaExamenController {

    private final CrearTematicaExamenUseCase crearUseCase;
    private final ActualizarTematicaExamenUseCase actualizarUseCase;
    private final ListarTematicasPorExamenUseCase listarUseCase;

    public TematicaExamenController(CrearTematicaExamenUseCase crearUseCase,
                                    ActualizarTematicaExamenUseCase actualizarUseCase,
                                    ListarTematicasPorExamenUseCase listarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<TematicaExamenDto> crear(@PathVariable UUID examenId,
                                                   @RequestBody CrearTematicaExamenDto request) {
        CrearTematicaExamenDto payload = new CrearTematicaExamenDto(
                examenId,
                request.titulo(),
                request.descripcion(),
                request.orden()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(payload));
    }

    @PutMapping("/{tematicaId}")
    public ResponseEntity<TematicaExamenDto> actualizar(@PathVariable UUID examenId,
                                                        @PathVariable UUID tematicaId,
                                                        @RequestBody ActualizarTematicaExamenDto request) {
        ActualizarTematicaExamenDto payload = new ActualizarTematicaExamenDto(
                tematicaId,
                examenId,
                request.titulo(),
                request.descripcion(),
                request.orden()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping
    public ResponseEntity<List<TematicaExamenDto>> listar(@PathVariable UUID examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }
}
