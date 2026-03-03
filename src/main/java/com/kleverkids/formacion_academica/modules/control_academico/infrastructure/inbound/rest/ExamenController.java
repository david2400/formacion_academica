package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona los exámenes")
@Tag(name = "Exámenes", description = "Gestiona los exámenes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes")
public class ExamenController {
    
    // Use Cases existentes
    private final CrearExamenUseCase crearExamenUseCase;
    private final CreateExamUseCase createExamUseCase;
    private final GetExamUseCase getExamUseCase;
    private final UpdateExamUseCase updateExamUseCase;
    private final DeleteExamUseCase deleteExamUseCase;
    private final SearchExamsUseCase searchExamsUseCase;
    private final StartExamUseCase startExamUseCase;
    private final SubmitExamUseCase submitExamUseCase;
    private final GradeExamUseCase gradeExamUseCase;
    private final GetExamResultsUseCase getExamResultsUseCase;
    private final RegistrarCalificacionPersonalizadaUseCase registrarCalificacionPersonalizadaUseCase;

    // Endpoints básicos
    @PostMapping
    public ResponseEntity<ExamenDto> crear(@Valid @RequestBody CrearExamenDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearExamenUseCase.crear(request));
    }
    
    @GetMapping("/{examenId}")
    public ResponseEntity<ExamResponse> consultar(@PathVariable UUID examenId) {
        ExamResponse response = getExamUseCase.getById(examenId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{examenId}")
    public ResponseEntity<ExamResponse> actualizar(@PathVariable UUID examenId, @Valid @RequestBody UpdateExamCommand command) {
        ExamResponse response = updateExamUseCase.update(examenId, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{examenId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID examenId) {
        deleteExamUseCase.delete(examenId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/calificaciones")
    public ResponseEntity<CalificacionPersonalizadaDto> registrarCalificacion(@Valid @RequestBody RegistrarCalificacionPersonalizadaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarCalificacionPersonalizadaUseCase.registrar(request));
    }
    
    // Endpoints adicionales del ExamController original
    @PostMapping("/{examenId}/iniciar")
    public ResponseEntity<ExamSubmissionResponse> iniciar(
            @PathVariable UUID examenId,
            @RequestParam UUID estudianteId) {
        ExamSubmissionResponse response = startExamUseCase.start(examenId, estudianteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/{examenId}/enviar")
    public ResponseEntity<ExamResultResponse> enviar(
            @PathVariable UUID examenId,
            @RequestBody SubmitExamCommand command) {
        ExamResultResponse response = submitExamUseCase.submit(examenId, command);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{examenId}/envios/{envioId}/calificar")
    public ResponseEntity<ExamResultResponse> calificar(
            @PathVariable UUID examenId,
            @PathVariable UUID envioId,
            @RequestBody GradeExamCommand command) {
        ExamResultResponse response = gradeExamUseCase.grade(examenId, envioId, command);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{examenId}/resultados")
    public ResponseEntity<List<ExamResultResponse>> getResultados(@PathVariable UUID examenId) {
        List<ExamResultResponse> response = getExamResultsUseCase.getResults(examenId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{examenId}/resultados/{estudianteId}")
    public ResponseEntity<ExamResultResponse> getResultadoEstudiante(
            @PathVariable UUID examenId,
            @PathVariable UUID estudianteId) {
        ExamResultResponse response = getExamResultsUseCase.getStudentResult(examenId, estudianteId);
        return ResponseEntity.ok(response);
    }
}
