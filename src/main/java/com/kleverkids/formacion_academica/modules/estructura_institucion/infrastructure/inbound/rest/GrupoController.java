package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ActualizarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.CrearGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Description(value = "Gestiona los grupos")
@Tag(name = "Grupos", description = "Gestiona los grupos")
@RestController
@RequestMapping("/estructura-institucion/grupos")
public class GrupoController {

    private final CrearGrupoUseCase crearGrupoUseCase;
    private final ActualizarGrupoUseCase actualizarGrupoUseCase;

    public GrupoController(CrearGrupoUseCase crearGrupoUseCase,
                           ActualizarGrupoUseCase actualizarGrupoUseCase) {
        this.crearGrupoUseCase = crearGrupoUseCase;
        this.actualizarGrupoUseCase = actualizarGrupoUseCase;
    }

    @PostMapping
    public ResponseEntity<GrupoDto> crear(@RequestBody CrearGrupoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearGrupoUseCase.crear(request));
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDto> actualizar(@PathVariable UUID grupoId,
                                               @RequestBody ActualizarGrupoDto request) {
        ActualizarGrupoDto payload = ActualizarGrupoDto.builder()
                .id(grupoId)
                .nombre(request.getNombre())
                .capacidadMaxima(request.getCapacidadMaxima())
                .tutorId(request.getTutorId())
                .aulaId(request.getAulaId())
                .build();
        return ResponseEntity.ok(actualizarGrupoUseCase.actualizar(payload));
    }
}
