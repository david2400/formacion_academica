package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Preguntas", description = "Gestiona las preguntas")
@Description(value = "Gestiona las preguntas")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/preguntas")
public class PreguntaController {
    
    // Use Cases del QuestionController original
    private final CreateQuestionUseCase createQuestionUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final GetQuestionUseCase getQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;
    private final SearchQuestionsUseCase searchQuestionsUseCase;
    private final ValidateAnswerUseCase validateAnswerUseCase;
    
    // Use Cases del PreguntaBancoController original
    private final CrearPreguntaBancoUseCase crearPreguntaBancoUseCase;
    private final ActualizarPreguntaBancoUseCase actualizarPreguntaBancoUseCase;
    private final ListarPreguntasPorTematicaUseCase listarPreguntasPorTematicaUseCase;
    private final ConsultarPreguntaBancoUseCase consultarPreguntaBancoUseCase;
    private final EliminarPreguntaBancoUseCase eliminarPreguntaBancoUseCase;

    // Endpoints CRUD básicos (del QuestionController)
    @PostMapping
    @Operation(summary = "Crear una nueva pregunta", description = "Crea una pregunta de cualquier tipo usando JSON polimórfico")
    public ResponseEntity<QuestionResponse> crear(@Valid @RequestBody CreateQuestionCommand command) {
        QuestionResponse response = createQuestionUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{preguntaId}")
    @Operation(summary = "Obtener pregunta por ID", description = "Recupera una pregunta por su identificador único")
    public ResponseEntity<QuestionResponse> consultar(@PathVariable UUID preguntaId) {
        QuestionResponse response = getQuestionUseCase.getById(preguntaId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{preguntaId}")
    @Operation(summary = "Actualizar una pregunta", description = "Actualiza una pregunta existente")
    public ResponseEntity<QuestionResponse> actualizar(@PathVariable UUID preguntaId,
                                                   @Valid @RequestBody UpdateQuestionCommand command) {
        QuestionResponse response = updateQuestionUseCase.update(preguntaId, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{preguntaId}")
    @Operation(summary = "Eliminar una pregunta", description = "Elimina una pregunta (soft delete)")
    public ResponseEntity<Void> eliminar(@PathVariable UUID preguntaId) {
        deleteQuestionUseCase.delete(preguntaId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{preguntaId}/validar")
    @Operation(summary = "Validar una respuesta", description = "Valida la respuesta de un estudiante contra la pregunta")
    public ResponseEntity<ValidationResult> validarRespuesta(@PathVariable UUID preguntaId,
                                                           @RequestBody AnswerValidationRequest request) {
        ValidationResult result = validateAnswerUseCase.validate(preguntaId, request);
        return ResponseEntity.ok(result);
    }
    
    // Endpoints del PreguntaBancoController (integrados en el mismo controlador)
    @PostMapping("/banco/{tematicaId}")
    public ResponseEntity<PreguntaBancoDto> crearEnBanco(@PathVariable UUID tematicaId,
                                                        @Valid @RequestBody CrearPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPreguntaBancoUseCase.crear(request));
    }

    @PutMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<PreguntaBancoDto> actualizarEnBanco(@PathVariable UUID tematicaId,
                                                             @PathVariable UUID preguntaId,
                                                             @Valid @RequestBody ActualizarPreguntaBancoDto request) {
        request.setTematicaId(tematicaId);
        request.setId(preguntaId);
        return ResponseEntity.ok(actualizarPreguntaBancoUseCase.actualizar(request));
    }

    @GetMapping("/banco/{tematicaId}")
    public ResponseEntity<List<PreguntaBancoDto>> listarPorTematica(@PathVariable UUID tematicaId) {
        return ResponseEntity.ok(listarPreguntasPorTematicaUseCase.listar(tematicaId));
    }

    @GetMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<PreguntaBancoDto> consultarEnBanco(@PathVariable UUID tematicaId,
                                                            @PathVariable UUID preguntaId) {
        return ResponseEntity.ok(consultarPreguntaBancoUseCase.consultarPorId(preguntaId));
    }

    @DeleteMapping("/banco/{tematicaId}/{preguntaId}")
    public ResponseEntity<Void> eliminarDelBanco(@PathVariable UUID tematicaId,
                                               @PathVariable UUID preguntaId) {
        eliminarPreguntaBancoUseCase.eliminar(preguntaId);
        return ResponseEntity.noContent().build();
    }
}
