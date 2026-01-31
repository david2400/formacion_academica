package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ActualizarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ListarObservacionesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.RegistrarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
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
@RequestMapping("/api/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/observaciones")
public class ObservacionCriterioController {

    private final RegistrarObservacionCriterioUseCase registrarUseCase;
    private final ActualizarObservacionCriterioUseCase actualizarUseCase;
    private final ListarObservacionesPorEstudianteUseCase listarUseCase;

    public ObservacionCriterioController(RegistrarObservacionCriterioUseCase registrarUseCase,
                                         ActualizarObservacionCriterioUseCase actualizarUseCase,
                                         ListarObservacionesPorEstudianteUseCase listarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<ObservacionCriterioDto> registrar(@PathVariable UUID examenId,
                                                            @PathVariable UUID estudianteId,
                                                            @RequestBody RegistrarObservacionCriterioDto request) {
        RegistrarObservacionCriterioDto payload = new RegistrarObservacionCriterioDto(
                examenId,
                request.criterioId(),
                estudianteId,
                request.puntaje(),
                request.observacion(),
                request.recomendacion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(payload));
    }

    @PutMapping("/{observacionId}")
    public ResponseEntity<ObservacionCriterioDto> actualizar(@PathVariable UUID examenId,
                                                             @PathVariable UUID estudianteId,
                                                             @PathVariable UUID observacionId,
                                                             @RequestBody ActualizarObservacionCriterioDto request) {
        ActualizarObservacionCriterioDto payload = new ActualizarObservacionCriterioDto(
                observacionId,
                examenId,
                request.criterioId(),
                estudianteId,
                request.puntaje(),
                request.observacion(),
                request.recomendacion()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping
    public ResponseEntity<List<ObservacionCriterioDto>> listar(@PathVariable UUID examenId,
                                                               @PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }
}
