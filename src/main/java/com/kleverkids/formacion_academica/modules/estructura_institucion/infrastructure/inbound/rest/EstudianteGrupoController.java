package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.AsignarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.CambiarEstadoEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudiantesPorGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
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
@RequestMapping("/api/estructura-institucion/estudiantes-grupo")
public class EstudianteGrupoController {

    private final AsignarEstudianteGrupoUseCase asignarUseCase;
    private final CambiarEstadoEstudianteGrupoUseCase cambiarEstadoUseCase;
    private final ListarEstudiantesPorGrupoUseCase listarUseCase;

    public EstudianteGrupoController(AsignarEstudianteGrupoUseCase asignarUseCase,
                                     CambiarEstadoEstudianteGrupoUseCase cambiarEstadoUseCase,
                                     ListarEstudiantesPorGrupoUseCase listarUseCase) {
        this.asignarUseCase = asignarUseCase;
        this.cambiarEstadoUseCase = cambiarEstadoUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<EstudianteGrupoDto> asignar(@RequestBody AsignarEstudianteGrupoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignarUseCase.asignar(request));
    }

    @PutMapping("/estado")
    public ResponseEntity<EstudianteGrupoDto> cambiarEstado(@RequestBody CambiarEstadoEstudianteGrupoDto request) {
        return ResponseEntity.ok(cambiarEstadoUseCase.cambiarEstado(request));
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<EstudianteGrupoDto>> listarPorGrupo(@PathVariable UUID grupoId) {
        return ResponseEntity.ok(listarUseCase.listar(grupoId));
    }
}
