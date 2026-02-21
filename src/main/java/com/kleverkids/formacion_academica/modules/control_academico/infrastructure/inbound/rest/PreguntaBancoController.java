package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ActualizarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ConsultarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.CrearPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.EliminarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ListarPreguntasPorTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
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

@Description(value = "Gestiona el banco de preguntas por temática")
@Tag(name = "Banco de Preguntas", description = "Gestiona el banco de preguntas por temática")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/tematicas/{tematicaId}/preguntas")
public class PreguntaBancoController {

    private final CrearPreguntaBancoUseCase crearUseCase;
    private final ActualizarPreguntaBancoUseCase actualizarUseCase;
    private final ListarPreguntasPorTematicaUseCase listarUseCase;
    private final ConsultarPreguntaBancoUseCase consultarUseCase;
    private final EliminarPreguntaBancoUseCase eliminarUseCase;


    @PostMapping
    public ResponseEntity<PreguntaBancoDto> crear(@PathVariable UUID tematicaId,
                                                  @PathVariable UUID examenId,
                                                  @Valid @RequestBody CrearPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{preguntaId}")
    public ResponseEntity<PreguntaBancoDto> actualizar(@PathVariable UUID tematicaId,
                                                       @PathVariable UUID examenId,
                                                       @PathVariable UUID preguntaId,
                                                       @Valid @RequestBody ActualizarPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        request.setId(preguntaId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<PreguntaBancoDto>> listar(@PathVariable UUID tematicaId,
                                                         @PathVariable UUID examenId) {
        return ResponseEntity.ok(listarUseCase.listar(tematicaId));
    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<PreguntaBancoDto> consultar(@PathVariable UUID tematicaId,
                                                      @PathVariable UUID examenId,
                                                      @PathVariable UUID preguntaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(preguntaId));
    }

    @DeleteMapping("/{preguntaId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID tematicaId,
                                         @PathVariable UUID examenId,
                                         @PathVariable UUID preguntaId) {
        eliminarUseCase.eliminar(preguntaId);
        return ResponseEntity.noContent().build();
    }
}
