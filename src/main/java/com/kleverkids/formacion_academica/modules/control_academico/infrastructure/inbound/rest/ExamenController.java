package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.CrearExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.RegistrarCalificacionPersonalizadaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/control-academico/examenes")
public class ExamenController {

    private final CrearExamenUseCase crearExamenUseCase;
    private final RegistrarCalificacionPersonalizadaUseCase registrarCalificacionPersonalizadaUseCase;

    public ExamenController(CrearExamenUseCase crearExamenUseCase,
                            RegistrarCalificacionPersonalizadaUseCase registrarCalificacionPersonalizadaUseCase) {
        this.crearExamenUseCase = crearExamenUseCase;
        this.registrarCalificacionPersonalizadaUseCase = registrarCalificacionPersonalizadaUseCase;
    }

    @PostMapping
    public ResponseEntity<ExamenDto> crear(@RequestBody CrearExamenDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamenUseCase.crear(request));
    }

    @PostMapping("/calificaciones")
    public ResponseEntity<CalificacionPersonalizadaDto> registrarCalificacion(@RequestBody RegistrarCalificacionPersonalizadaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarCalificacionPersonalizadaUseCase.registrar(request));
    }
}
