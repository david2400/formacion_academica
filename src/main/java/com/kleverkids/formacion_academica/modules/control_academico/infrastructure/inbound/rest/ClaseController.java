package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/control-academico/clases")
public class ClaseController {

    private final CrearClaseUseCase crearClaseUseCase;
    private final CrearClasesMasivasUseCase crearClasesMasivasUseCase;

    public ClaseController(CrearClaseUseCase crearClaseUseCase,
                           CrearClasesMasivasUseCase crearClasesMasivasUseCase) {
        this.crearClaseUseCase = crearClaseUseCase;
        this.crearClasesMasivasUseCase = crearClasesMasivasUseCase;
    }

    @PostMapping
    public ResponseEntity<ClaseDto> crearClase(@RequestBody CrearClaseDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearClaseUseCase.crearClase(request));
    }

    @PostMapping("/masivas")
    public ResponseEntity<ResultadoClasesMasivasDto> crearClasesMasivas(@RequestBody CrearClasesMasivasDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearClasesMasivasUseCase.crearClases(request));
    }
}
