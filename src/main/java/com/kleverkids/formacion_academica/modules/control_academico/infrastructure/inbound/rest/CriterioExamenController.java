package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ActualizarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ConsultarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.CrearCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.EliminarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona los criterios de evaluación de un examen")
@Tag(name = "Criterios de Examen", description = "Gestiona los criterios de evaluación de un examen")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/criterios")
public class CriterioExamenController {

    private final CrearCriterioExamenUseCase crearUseCase;
    private final ActualizarCriterioExamenUseCase actualizarUseCase;
    private final ListarCriteriosPorExamenUseCase listarUseCase;
    private final ConsultarCriterioExamenUseCase consultarUseCase;
    private final EliminarCriterioExamenUseCase eliminarUseCase;

    @PostMapping
    public ResponseEntity<CriterioExamenDto> crear(@PathVariable UUID examenId,
                                                   @Valid @RequestBody CrearCriterioExamenDto request) {
        request.setExamenId(examenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{criterioId}")
    public ResponseEntity<CriterioExamenDto> actualizar(@PathVariable UUID examenId,
                                                        @PathVariable UUID criterioId,
                                                        @Valid @RequestBody ActualizarCriterioExamenDto request) {
        request.setExamenId(examenId);
        request.setId(criterioId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<CriterioExamenDto>> listar(@PathVariable UUID examenId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId));
    }

    @GetMapping("/{criterioId}")
    public ResponseEntity<CriterioExamenDto> consultar(@PathVariable UUID examenId,
                                                       @PathVariable UUID criterioId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(criterioId));
    }

    @DeleteMapping("/{criterioId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID examenId,
                                         @PathVariable UUID criterioId) {
        eliminarUseCase.eliminar(criterioId);
        return ResponseEntity.noContent().build();
    }
}
