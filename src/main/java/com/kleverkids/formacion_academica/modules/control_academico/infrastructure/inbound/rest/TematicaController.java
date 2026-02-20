package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
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

@Description(value = "Gestiona las temáticas asociadas a un examen")
@Tag(name = "Temáticas", description = "Gestiona las temáticas asociadas a un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/tematicas")
public class TematicaController {

    private final CrearTematicaUseCase crearUseCase;
    private final ActualizarTematicaUseCase actualizarUseCase;
    private final ListarTematicasUseCase listarUseCase;

    @PostMapping
    public ResponseEntity<TematicaDto> crear(@PathVariable UUID examenId,
                                                   @Valid @RequestBody CrearTematicaDto request) {

        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{tematicaId}")
    public ResponseEntity<TematicaDto> actualizar(@PathVariable UUID examenId,
                                                        @PathVariable UUID tematicaId,
                                                        @Valid @RequestBody ActualizarTematicaDto request) {

        request.setExamenId(examenId);
        request.setId(tematicaId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<TematicaDto>> listar(@PathVariable UUID examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }
}
