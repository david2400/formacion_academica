package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarHistorialAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.RegistrarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/control-academico/asistencias")
public class AsistenciaController {

    private final RegistrarAsistenciaUseCase registrarAsistenciaUseCase;
    private final ConsultarHistorialAsistenciaUseCase historialAsistenciaUseCase;

    public AsistenciaController(RegistrarAsistenciaUseCase registrarAsistenciaUseCase,
                                ConsultarHistorialAsistenciaUseCase historialAsistenciaUseCase) {
        this.registrarAsistenciaUseCase = registrarAsistenciaUseCase;
        this.historialAsistenciaUseCase = historialAsistenciaUseCase;
    }

    @PostMapping
    public ResponseEntity<AsistenciaDto> registrar(@RequestBody RegistrarAsistenciaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarAsistenciaUseCase.registrar(request));
    }

    @GetMapping("/historial")
    public ResponseEntity<HistorialAsistenciaDto> consultarHistorial(@RequestBody HistorialAsistenciaFiltroDto filtro) {
        return ResponseEntity.ok(historialAsistenciaUseCase.consultar(filtro));
    }
}
