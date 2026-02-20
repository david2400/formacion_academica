package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.CrearExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.RegistrarCalificacionPersonalizadaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Description(value = "Gestiona los exámenes")
@Tag(name = "Exámenes", description = "Gestiona los exámenes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes")
public class ExamenController {

    private final CrearExamenUseCase crearExamenUseCase;
    private final RegistrarCalificacionPersonalizadaUseCase registrarCalificacionPersonalizadaUseCase;


    @PostMapping
    public ResponseEntity<ExamenDto> crear(@Valid @RequestBody CrearExamenDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamenUseCase.crear(request));
    }

    @PostMapping("/calificaciones")
    public ResponseEntity<CalificacionPersonalizadaDto> registrarCalificacion(@Valid @RequestBody RegistrarCalificacionPersonalizadaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarCalificacionPersonalizadaUseCase.registrar(request));
    }
}
