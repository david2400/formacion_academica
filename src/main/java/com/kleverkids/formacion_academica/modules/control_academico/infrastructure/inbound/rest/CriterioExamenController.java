package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ActualizarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.CrearCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
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
@RequestMapping("/api/control-academico/examenes/{examenId}/criterios")
public class CriterioExamenController {

    private final CrearCriterioExamenUseCase crearUseCase;
    private final ActualizarCriterioExamenUseCase actualizarUseCase;
    private final ListarCriteriosPorExamenUseCase listarUseCase;

    public CriterioExamenController(CrearCriterioExamenUseCase crearUseCase,
                                    ActualizarCriterioExamenUseCase actualizarUseCase,
                                    ListarCriteriosPorExamenUseCase listarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<CriterioExamenDto> crear(@PathVariable UUID examenId,
                                                   @RequestBody CrearCriterioExamenDto request) {
        CrearCriterioExamenDto payload = new CrearCriterioExamenDto(
                examenId,
                request.nombre(),
                request.descripcion(),
                request.ponderacion(),
                request.orden(),
                request.recomendacionBase()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(payload));
    }

    @PutMapping("/{criterioId}")
    public ResponseEntity<CriterioExamenDto> actualizar(@PathVariable UUID examenId,
                                                        @PathVariable UUID criterioId,
                                                        @RequestBody ActualizarCriterioExamenDto request) {
        ActualizarCriterioExamenDto payload = new ActualizarCriterioExamenDto(
                criterioId,
                examenId,
                request.nombre(),
                request.descripcion(),
                request.ponderacion(),
                request.orden(),
                request.recomendacionBase()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping
    public ResponseEntity<List<CriterioExamenDto>> listar(@PathVariable UUID examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }
}
