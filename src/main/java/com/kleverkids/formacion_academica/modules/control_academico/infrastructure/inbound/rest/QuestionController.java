package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
@Tag(name = "Questions", description = "Question management endpoints")
public class QuestionController {
    
    private final CreateQuestionUseCase createQuestionUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final GetQuestionUseCase getQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;
    private final SearchQuestionsUseCase searchQuestionsUseCase;
    private final ValidateAnswerUseCase validateAnswerUseCase;

    
    @PostMapping
    @Operation(summary = "Create a new question", description = "Creates a question of any supported type using polymorphic JSON")
    public ResponseEntity<QuestionResponse> create(@Valid @RequestBody CreateQuestionCommand command) {
        QuestionResponse response = createQuestionUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get question by ID", description = "Retrieves a question by its unique identifier")
    public ResponseEntity<QuestionResponse> getById(@PathVariable UUID id) {
        QuestionResponse response = getQuestionUseCase.getById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a question", description = "Updates an existing question")
    public ResponseEntity<QuestionResponse> update(@PathVariable UUID id,
                                                   @Valid @RequestBody UpdateQuestionCommand command) {
        QuestionResponse response = updateQuestionUseCase.update(id, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a question", description = "Soft deletes a question")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteQuestionUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    @Operation(summary = "Search questions", description = "Search and filter questions with pagination")
    public ResponseEntity<Page<QuestionResponse>> search(
            @RequestParam(required = false) String questionType,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) UUID themeId,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            Pageable pageable) {
        
        QuestionSearchCriteria criteria = new QuestionSearchCriteria(
            questionType, difficulty, themeId, tags, searchText, includeDeleted
        );
        
        Page<QuestionResponse> response = searchQuestionsUseCase.search(criteria, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/validate")
    @Operation(summary = "Validate an answer", description = "Validates a student's answer against the question")
    public ResponseEntity<ValidationResult> validateAnswer(@PathVariable UUID id,
                                                           @RequestBody AnswerValidationRequest request) {
        ValidationResult result = validateAnswerUseCase.validate(id, request);
        return ResponseEntity.ok(result);
    }
}
