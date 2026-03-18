package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamResultRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamSubmissionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.QuestionAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.ExamStatus;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.TimeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ExamenService implements ConsultarExamenUseCase, ActualizarExamenUseCase,
        EliminarExamenUseCase, BuscarExamenesUseCase, IniciarExamenUseCase, EnviarExamenUseCase,
        CalificarExamenUseCase, ObtenerResultadosExamenUseCase, CrearExamenUseCase, RegistrarCalificacionPersonalizadaUseCase {
    
    // Use Cases del sistema en inglés
    private final ExamRepositoryPort examRepository;
    private final ExamSubmissionRepositoryPort submissionRepository;
    private final ExamResultRepositoryPort resultRepository;
    private final ServicioCalificacionExamen scoringService;
    
    // Use Cases del sistema en español
    private final ExamenRepositoryPort examenRepositoryPort;
    
    // Implementación de Use Cases en inglés
    public ExamResponse create(CreateExamCommand command) {
        Exam exam = new Exam(
            null,
            command.name(),
            command.code(),
            command.subject(),
            command.gradeLevel(),
            command.instructions(),
            ExamStatus.DRAFT,
            mapTimeConfig(command.timeConfig()),
            mapQuestions(command.questions()),
            mapCriteria(command.criteria())
        );
        
        exam.validate();
        Exam saved = examRepository.save(exam);
        return ExamResponse.fromDomain(saved);
    }
    
    @Transactional(readOnly = true)
    public ExamResponse getById(Long id) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new ExamNotFoundException(id));
        return ExamResponse.fromDomain(exam);
    }
    
    public ExamResponse update(Long id, UpdateExamCommand command) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new ExamNotFoundException(id));
        
        if (command.name() != null) exam.setName(command.name());
        if (command.code() != null) exam.setCode(command.code());
        if (command.subject() != null) exam.setSubject(command.subject());
        if (command.gradeLevel() != null) exam.setGradeLevel(command.gradeLevel());
        if (command.instructions() != null) exam.setInstructions(command.instructions());
        if (command.timeConfig() != null) exam.setTimeConfig(mapTimeConfig(command.timeConfig()));
        if (command.questions() != null) exam.setQuestions(mapQuestions(command.questions()));
        if (command.criteria() != null) exam.setCriteria(mapCriteria(command.criteria()));
        
        exam.validate();
        Exam saved = examRepository.save(exam);
        return ExamResponse.fromDomain(saved);
    }
    
    public void delete(Long id) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new ExamNotFoundException(id));
        exam.markAsDeleted();
        examRepository.save(exam);
    }
    
    @Transactional(readOnly = true)
    public Page<ExamResponse> search(ExamSearchCriteria criteria, Pageable pageable) {
        return examRepository.search(criteria, pageable)
            .map(ExamResponse::fromDomain);
    }
    
    public ExamSubmissionResponse start(Long examId, Long studentId) {
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ExamNotFoundException(examId));
        
        if (exam.getStatus() != ExamStatus.ACTIVE) {
            throw new IllegalStateException("El examen no está activo");
        }
        
        ExamSubmission submission = new ExamSubmission(null, examId, studentId);
        ExamSubmission saved = submissionRepository.save(submission);
        return ExamSubmissionResponse.fromDomain(saved);
    }
    
    public ExamResultResponse submit(Long examId, SubmitExamCommand command) {
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ExamNotFoundException(examId));
        
        ExamSubmission submission = submissionRepository.findById(command.submissionId())
            .orElseThrow(() -> new IllegalArgumentException("Entrega no encontrada"));
        
        // Add answers to submission
        for (QuestionAnswerDto answerDto : command.answers()) {
            QuestionAnswer answer = new QuestionAnswer(null, answerDto.questionId(), extractAnswer(answerDto));
            submission.addAnswer(answer);
        }
        
        submission.submit();
        submissionRepository.save(submission);
        
        // Calculate result
        ExamResult result = scoringService.calculateResult(examId, submission, exam.getTotalPoints());
        result.setGrade(scoringService.calculateGrade(result.getPercentage()));
        ExamResult savedResult = resultRepository.save(result);
        
        return ExamResultResponse.fromDomain(savedResult);
    }
    
    public ExamResultResponse grade(Long examId, Long submissionId, GradeExamCommand command) {
        ExamSubmission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> new IllegalArgumentException("Entrega no encontrada"));
        
        BigDecimal totalScore = BigDecimal.ZERO;
        for (GradeExamCommand.QuestionGradeDto grade : command.grades()) {
            for (QuestionAnswer answer : submission.getAnswers()) {
                if (answer.getQuestionId().equals(grade.questionId())) {
                    answer.grade(grade.score(), grade.feedback());
                    totalScore = totalScore.add(grade.score());
                }
            }
        }
        
        submission.grade(totalScore);
        submissionRepository.save(submission);
        
        ExamResult result = resultRepository.findByExamIdAndStudentId(examId, submission.getStudentId())
            .orElseThrow(() -> new IllegalArgumentException("Resultado no encontrado"));
        
        result.setTotalScore(totalScore);
        result.setGrade(scoringService.calculateGrade(result.getPercentage()));
        result.setGradedBy(command.gradedBy());
        
        ExamResult savedResult = resultRepository.save(result);
        return ExamResultResponse.fromDomain(savedResult);
    }
    
    @Transactional(readOnly = true)
    public List<ExamResultResponse> getResults(Long examId) {
        return resultRepository.findByExamId(examId).stream()
            .map(ExamResultResponse::fromDomain)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ExamResultResponse getStudentResult(Long examId, Long studentId) {
        ExamResult result = resultRepository.findByExamIdAndStudentId(examId, studentId)
            .orElseThrow(() -> new IllegalArgumentException("Resultado no encontrado"));
        return ExamResultResponse.fromDomain(result);
    }
    
    // Implementación de Use Cases en español
    @Override
    public Examen crear(CrearExamenDto request) {
        return examenRepositoryPort.guardar(request);
    }

    @Override
    public CalificacionPersonalizadaDto registrar(RegistrarCalificacionPersonalizadaDto request) {
        return examenRepositoryPort.registrarCalificacion(request);
    }
    
    // Métodos auxiliares
    private TimeConfig mapTimeConfig(TimeConfigDto dto) {
        if (dto == null) return null;
        return TimeConfig.create(dto.duration(), dto.scheduledDate(), dto.startTime(), dto.endTime());
    }
    
    private List<ExamQuestion> mapQuestions(List<ExamQuestionDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> new ExamQuestion(d.id(), d.questionId(), d.order(), d.points(), d.required()))
            .collect(Collectors.toList());
    }
    
    private List<EvaluationCriteria> mapCriteria(List<EvaluationCriteriaDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> EvaluationCriteria.create(d.id(), d.name(), d.description(), d.weight(), d.maxScore()))
            .collect(Collectors.toList());
    }
    
    // Implementación de Use Cases en español
    @Override
    public ExamResponse consultarPorId(Long id) {
        return getById(id);
    }
    
    @Override
    public ExamResponse actualizar(Long id, UpdateExamCommand command) {
        return update(id, command);
    }
    
    @Override
    public void eliminar(Long id) {
        delete(id);
    }
    
    @Override
    public Page<ExamResponse> buscar(ExamSearchCriteria criterios, Pageable pageable) {
        return search(criterios, pageable);
    }
    
    @Override
    public ExamSubmissionResponse iniciar(Long examenId, Long estudianteId) {
        return start(examenId, estudianteId);
    }
    
    @Override
    public ExamResultResponse enviar(Long examenId, SubmitExamCommand command) {
        return submit(examenId, command);
    }
    
    @Override
    public ExamResultResponse calificar(Long examenId, Long envioId, GradeExamCommand command) {
        return grade(examenId, envioId, command);
    }
    
    @Override
    public List<ExamResultResponse> obtenerResultados(Long examenId) {
        return getResults(examenId);
    }
    
    @Override
    public ExamResultResponse obtenerResultadoEstudiante(Long examenId, Long estudianteId) {
        return getStudentResult(examenId, estudianteId);
    }
    
    private Object extractAnswer(QuestionAnswerDto dto) {
        if (dto.selectedOptionId() != null) return dto.selectedOptionId();
        if (dto.selectedOptionIds() != null) return dto.selectedOptionIds();
        if (dto.booleanAnswer() != null) return dto.booleanAnswer();
        if (dto.textAnswer() != null) return dto.textAnswer();
        if (dto.numericAnswer() != null) return dto.numericAnswer();
        if (dto.scaleValue() != null) return dto.scaleValue();
        if (dto.orderedItemIds() != null) return dto.orderedItemIds();
        if (dto.matchedPairs() != null) return dto.matchedPairs();
        return null;
    }
}
