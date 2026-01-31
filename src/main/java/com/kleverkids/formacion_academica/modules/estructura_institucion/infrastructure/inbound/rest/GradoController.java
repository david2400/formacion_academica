package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ActualizarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.CrearGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/estructura-institucion/grados")
public class GradoController {

    private final CrearGradoUseCase crearGradoUseCase;
    private final ActualizarGradoUseCase actualizarGradoUseCase;

    public GradoController(CrearGradoUseCase crearGradoUseCase,
                           ActualizarGradoUseCase actualizarGradoUseCase) {
        this.crearGradoUseCase = crearGradoUseCase;
        this.actualizarGradoUseCase = actualizarGradoUseCase;
    }

    @PostMapping
    public ResponseEntity<GradoDto> crear(@RequestBody CrearGradoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearGradoUseCase.crear(request));
    }

    @PutMapping("/{gradoId}")
    public ResponseEntity<GradoDto> actualizar(@PathVariable UUID gradoId,
                                               @RequestBody ActualizarGradoDto request) {
        ActualizarGradoDto payload = new ActualizarGradoDto(
                gradoId,
                request.nombre(),
                request.nivelEducativo(),
                request.orden()
        );
        return ResponseEntity.ok(actualizarGradoUseCase.actualizar(payload));
    }
}
