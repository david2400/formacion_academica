package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ActualizarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.CrearPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ListarPreguntasPorTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/control-academico/tematicas/{tematicaId}/preguntas")
public class PreguntaBancoController {

    private final CrearPreguntaBancoUseCase crearUseCase;
    private final ActualizarPreguntaBancoUseCase actualizarUseCase;
    private final ListarPreguntasPorTematicaUseCase listarUseCase;


    @PostMapping
    public ResponseEntity<PreguntaBancoDto> crear(@PathVariable UUID tematicaId,
                                                  @Valid @RequestBody CrearPreguntaBancoDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{preguntaId}")
    public ResponseEntity<PreguntaBancoDto> actualizar(@PathVariable UUID tematicaId,
                                                       @PathVariable UUID preguntaId,
                                                       @Valid @RequestBody ActualizarPreguntaBancoDto request) {

        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<PreguntaBancoDto>> listar(@PathVariable UUID tematicaId) {
        return ResponseEntity.ok(listarUseCase.listar(tematicaId));
    }
}
