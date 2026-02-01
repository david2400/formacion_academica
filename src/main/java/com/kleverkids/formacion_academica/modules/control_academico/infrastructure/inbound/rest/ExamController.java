package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
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
@RequestMapping("/api/v1/exams")
public class ExamController {
    
    private final CreateExamUseCase createExamUseCase;
    private final GetExamUseCase getExamUseCase;
    private final UpdateExamUseCase updateExamUseCase;
    private final DeleteExamUseCase deleteExamUseCase;
    private final SearchExamsUseCase searchExamsUseCase;
    private final StartExamUseCase startExamUseCase;
    private final SubmitExamUseCase submitExamUseCase;
    private final GradeExamUseCase gradeExamUseCase;
    private final GetExamResultsUseCase getExamResultsUseCase;

    @PostMapping
    public ResponseEntity<ExamResponse> create(@Valid @RequestBody CreateExamCommand command) {
        ExamResponse response = createExamUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExamResponse> getById(@PathVariable UUID id) {
        ExamResponse response = getExamUseCase.getById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExamResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateExamCommand command) {
        ExamResponse response = updateExamUseCase.update(id, command);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteExamUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<Page<ExamResponse>> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String gradeLevel,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            Pageable pageable) {
        ExamSearchCriteria criteria = new ExamSearchCriteria(status, subject, gradeLevel, searchText, includeDeleted);
        Page<ExamResponse> response = searchExamsUseCase.search(criteria, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{examId}/start")
    public ResponseEntity<ExamSubmissionResponse> start(
            @PathVariable UUID examId,
            @RequestParam UUID studentId) {
        ExamSubmissionResponse response = startExamUseCase.start(examId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/{examId}/submit")
    public ResponseEntity<ExamResultResponse> submit(
            @PathVariable UUID examId,
            @RequestBody SubmitExamCommand command) {
        ExamResultResponse response = submitExamUseCase.submit(examId, command);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{examId}/submissions/{submissionId}/grade")
    public ResponseEntity<ExamResultResponse> grade(
            @PathVariable UUID examId,
            @PathVariable UUID submissionId,
            @RequestBody GradeExamCommand command) {
        ExamResultResponse response = gradeExamUseCase.grade(examId, submissionId, command);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{examId}/results")
    public ResponseEntity<List<ExamResultResponse>> getResults(@PathVariable UUID examId) {
        List<ExamResultResponse> response = getExamResultsUseCase.getResults(examId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{examId}/results/{studentId}")
    public ResponseEntity<ExamResultResponse> getStudentResult(
            @PathVariable UUID examId,
            @PathVariable UUID studentId) {
        ExamResultResponse response = getExamResultsUseCase.getStudentResult(examId, studentId);
        return ResponseEntity.ok(response);
    }
}
