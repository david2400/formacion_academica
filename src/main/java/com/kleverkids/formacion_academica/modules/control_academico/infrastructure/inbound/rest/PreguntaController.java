package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Preguntas", description = "Gestiona las preguntas")
@Description(value = "Gestiona las preguntas")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/preguntas")
public class PreguntaController {
    
    // Use Cases del QuestionController original
    private final CrearPreguntaUseCase crearPreguntaUseCase;
    private final ActualizarPreguntaUseCase actualizarPreguntaUseCase;
    private final ConsultarPreguntaUseCase consultarPreguntaUseCase;
    private final EliminarPreguntaUseCase eliminarPreguntaUseCase;
    private final BuscarPreguntasUseCase buscarPreguntasUseCase;
    private final ValidarRespuestaUseCase validarRespuestaUseCase;
    
    // Use Cases del PreguntaBancoController original
    private final CrearPreguntaBancoUseCase crearPreguntaBancoUseCase;
    private final ActualizarPreguntaBancoUseCase actualizarPreguntaBancoUseCase;
    private final ListarPreguntasPorTematicaUseCase listarPreguntasPorTematicaUseCase;
    private final ConsultarPreguntaBancoUseCase consultarPreguntaBancoUseCase;
    private final EliminarPreguntaBancoUseCase eliminarPreguntaBancoUseCase;

    // Endpoints CRUD básicos (del QuestionController)
    @PostMapping
    @Operation(summary = "Crear una nueva pregunta", description = "Crea una pregunta de cualquier tipo usando JSON polimórfico")
    public ResponseEntity<RespuestaPregunta> crear(@Valid @RequestBody CreateQuestionCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPreguntaUseCase.create(command));
    }
    
    @GetMapping("/{preguntaId}")
    @Operation(summary = "Obtener pregunta por ID", description = "Recupera una pregunta por su identificador único")
    public ResponseEntity<RespuestaPregunta> consultar(@PathVariable Long preguntaId) {
        return ResponseEntity.ok(consultarPreguntaUseCase.consultarPorId(preguntaId));
    }
    
    @PutMapping("/{preguntaId}")
    @Operation(summary = "Actualizar una pregunta", description = "Actualiza una pregunta existente")
    public ResponseEntity<RespuestaPregunta> actualizar(@PathVariable Long preguntaId,
                                                   @Valid @RequestBody UpdateQuestionCommand command) {
        return ResponseEntity.ok(actualizarPreguntaUseCase.actualizar(preguntaId, command));
    }
    
    @DeleteMapping("/{preguntaId}")
    @Operation(summary = "Eliminar una pregunta", description = "Elimina una pregunta (soft delete)")
    public ResponseEntity<Void> eliminar(@PathVariable Long preguntaId) {
        eliminarPreguntaUseCase.eliminar(preguntaId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{preguntaId}/validar")
    @Operation(summary = "Validar una respuesta", description = "Valida la respuesta de un estudiante contra la pregunta")
    public ResponseEntity<ValidationResult> validarRespuesta(@PathVariable Long preguntaId,
                                                           @RequestBody SolicitudValidacionRespuesta request) {
        ValidationResult result = validarRespuestaUseCase.validar(preguntaId, request);
        return ResponseEntity.ok(result);
    }
    
    // Endpoints del PreguntaBancoController (integrados en el mismo controlador)
    @PostMapping("/banco/{tematicaId}")
    public ResponseEntity<PreguntaBanco> crearEnBanco(@PathVariable Long tematicaId,
                                                        @Valid @RequestBody CrearPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPreguntaBancoUseCase.crear(request));
    }

    @PutMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<PreguntaBanco> actualizarEnBanco(@PathVariable Long tematicaId,
                                                             @PathVariable Long preguntaId,
                                                             @Valid @RequestBody ActualizarPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        request.setId(preguntaId);
        return ResponseEntity.ok(actualizarPreguntaBancoUseCase.actualizar(request));
    }

    @GetMapping("/banco/{tematicaId}")
    public ResponseEntity<List<PreguntaBanco>> listarPorTematica(@PathVariable Long tematicaId) {
        return ResponseEntity.ok(listarPreguntasPorTematicaUseCase.listar(tematicaId));
    }

    @GetMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<PreguntaBanco> consultarEnBanco(@PathVariable Long tematicaId,
                                                            @PathVariable Long preguntaId) {
        return ResponseEntity.ok(consultarPreguntaBancoUseCase.consultarPreguntaBancoPorId(preguntaId));
    }

    @DeleteMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<Void> eliminarDelBanco(@PathVariable Long tematicaId,
                                               @PathVariable Long preguntaId) {
        eliminarPreguntaBancoUseCase.eliminarPreguntaBanco(preguntaId);
        return ResponseEntity.noContent().build();
    }
}
