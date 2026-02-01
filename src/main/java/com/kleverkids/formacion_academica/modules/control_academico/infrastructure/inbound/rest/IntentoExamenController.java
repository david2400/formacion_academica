package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.FinalizarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.IniciarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.ListarIntentosPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.RegistrarRespuestaIntentoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/intentos")
public class IntentoExamenController {

    private final IniciarIntentoExamenUseCase iniciarUseCase;
    private final RegistrarRespuestaIntentoUseCase registrarRespuestaUseCase;
    private final FinalizarIntentoExamenUseCase finalizarUseCase;
    private final ListarIntentosPorEstudianteUseCase listarUseCase;

    @PostMapping
    public ResponseEntity<IntentoExamenDto> iniciar(@PathVariable UUID examenId,
                                                    @PathVariable UUID estudianteId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iniciarUseCase.iniciar(new IniciarIntentoExamenDto(examenId, estudianteId)));
    }

    @PostMapping("/{intentoId}/respuestas")
    public ResponseEntity<RespuestaIntentoDto> registrarRespuesta(@PathVariable UUID intentoId,
                                                                  @Valid @RequestBody RegistrarRespuestaIntentoDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(registrarRespuestaUseCase.registrar(request));
    }

    @PostMapping("/{intentoId}/finalizar")
    public ResponseEntity<IntentoExamenDto> finalizar(@PathVariable UUID intentoId,
                                                      @RequestBody(required = false) FinalizarIntentoExamenDto request) {
        Integer puntajeTotal = request != null ? request.puntajeTotal() : null;
        return ResponseEntity.ok(finalizarUseCase.finalizar(new FinalizarIntentoExamenDto(intentoId, puntajeTotal)));
    }

    @GetMapping
    public ResponseEntity<List<IntentoExamenDto>> listar(@PathVariable UUID examenId,
                                                         @PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }
}
