package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ResultadoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.EnvioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.PreguntaExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.TimeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExamenService implements
        ConsultarExamenUseCase, ActualizarExamenUseCase,
        EliminarExamenUseCase, IniciarExamenUseCase, EnviarExamenUseCase,
        CalificarExamenUseCase, ObtenerResultadosExamenUseCase, CrearExamenUseCase,
        RegistrarCalificacionPersonalizadaUseCase,
        BuscarExamenesUseCase {

    private final ExamenRepositoryPort examenRepository;
    private final EnvioExamenRepositoryPort submissionRepository;
    private final ResultadoExamenRepositoryPort resultRepository;
    private final ServicioCalificacionExamen scoringService;

    @Override
    public Examen crear(CrearExamenDto request) {
        log.info("Creando examen: {}", request);
        return examenRepository.guardar(request);
    }

    @Override
    public ExamResponse consultarPorId(Long id) {
        log.info("Consultando examen por ID: {}", id);
        Exam exam = examenRepository.findExamById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
        return ExamResponse.fromDomain(exam);
    }

    @Override
    public ExamResponse actualizar(Long id, UpdateExamCommand command) {
        log.info("Actualizando examen ID: {}", id);
        Exam exam = examenRepository.findExamById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
        applyUpdate(exam, command);
        Exam saved = examenRepository.save(exam);
        return ExamResponse.fromDomain(saved);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando examen ID: {}", id);
        examenRepository.deleteById(id);
    }

    @Override
    public ExamSubmissionResponse iniciar(Long examenId, Long estudianteId) {
        log.info("Iniciando examen ID: {} para estudiante: {}", examenId, estudianteId);
        examenRepository.findExamById(examenId).orElseThrow(() -> new ExamNotFoundException(examenId));
        EnvioExamen envio = submissionRepository.findByExamIdAndStudentId(examenId, estudianteId)
                .orElseGet(() -> submissionRepository.guardar(new EnvioExamen(null, examenId, estudianteId)));
        return ExamSubmissionResponse.fromDomain(envio);
    }

    @Override
    public ExamResultResponse enviar(Long examenId, SubmitExamCommand command) {
        log.info("Enviando examen ID: {}", examenId);
        EnvioExamen submission = submissionRepository.findById(command.submissionId())
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado: " + command.submissionId()));
        if (!examenId.equals(submission.getExamenId())
                || !command.studentId().equals(submission.getEstudianteId())) {
            throw new IllegalArgumentException("El envío no corresponde al examen o al estudiante");
        }
        submission.setRespuestas(mapAnswers(command));
        submission.entregar();
        submissionRepository.guardar(submission);

        Exam exam = examenRepository.findExamById(examenId).orElseThrow(() -> new ExamNotFoundException(examenId));
        ResultadoExamen resultado = scoringService.calculateResult(examenId, submission, exam.getTotalPoints());
        resultado = resultRepository.save(resultado);
        return ExamResultResponse.fromDomain(resultado);
    }

    @Override
    public ExamResultResponse calificar(Long examenId, Long envioId, GradeExamCommand command) {
        log.info("Calificando examen ID: {} envío: {}", examenId, envioId);
        EnvioExamen submission = submissionRepository.findById(envioId)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado: " + envioId));
        if (!examenId.equals(submission.getExamenId())) {
            throw new IllegalArgumentException("El envío no pertenece al examen indicado");
        }
        Exam exam = examenRepository.findExamById(examenId).orElseThrow(() -> new ExamNotFoundException(examenId));

        if (command.grades() != null) {
            for (GradeExamCommand.QuestionGradeDto grade : command.grades()) {
                submission.getRespuestas().stream()
                        .filter(r -> grade.questionId().equals(r.getPreguntaId()))
                        .findFirst()
                        .ifPresent(r -> r.grade(grade.score(), grade.feedback()));
            }
        }

        ResultadoExamen resultado = scoringService.calculateResult(examenId, submission, exam.getTotalPoints());
        if (command.gradedBy() != null) {
            resultado.setCalificadoPor(command.gradedBy());
        }
        resultado = resultRepository.save(resultado);
        return ExamResultResponse.fromDomain(resultado);
    }

    @Override
    public List<ExamResultResponse> obtenerResultados(Long examenId) {
        log.info("Obteniendo resultados del examen ID: {}", examenId);
        return resultRepository.findByExamId(examenId).stream()
                .map(ExamResultResponse::fromDomain)
                .toList();
    }

    @Override
    public ExamResultResponse obtenerResultadoEstudiante(Long examenId, Long estudianteId) {
        log.info("Obteniendo resultado del examen ID: {} para estudiante: {}", examenId, estudianteId);
        return resultRepository.findByExamIdAndStudentId(examenId, estudianteId)
                .map(ExamResultResponse::fromDomain)
                .orElse(null);
    }

    @Override
    public CalificacionPersonalizadaDto registrar(RegistrarCalificacionPersonalizadaDto request) {
        log.info("Registrando calificación personalizada: {}", request);
        return examenRepository.registrarCalificacion(request);
    }

    @Override
    public Page<ExamResponse> buscar(ExamSearchCriteria criterios, Pageable pageable) {
        log.info("Buscando exámenes con criterios: {}", criterios);
        return examenRepository.searchExams(criterios, pageable).map(ExamResponse::fromDomain);
    }

    private static void applyUpdate(Exam exam, UpdateExamCommand cmd) {
        if (cmd.name() != null) {
            exam.setName(cmd.name());
        }
        if (cmd.code() != null) {
            exam.setCode(cmd.code());
        }
        if (cmd.subject() != null) {
            exam.setSubject(cmd.subject());
        }
        if (cmd.gradeLevel() != null) {
            exam.setGradeLevel(cmd.gradeLevel());
        }
        if (cmd.instructions() != null) {
            exam.setInstructions(cmd.instructions());
        }
        if (cmd.timeConfig() != null) {
            TimeConfigDto tc = cmd.timeConfig();
            exam.setTimeConfig(TimeConfig.create(tc.duration(), tc.scheduledDate(), tc.startTime(), tc.endTime()));
        }
        if (cmd.questions() != null) {
            List<PreguntaExamen> nuevas = new ArrayList<>();
            for (ExamQuestionDto q : cmd.questions()) {
                nuevas.add(new PreguntaExamen(q.id(), q.questionId(), q.order(), q.points(), q.required()));
            }
            exam.setQuestions(nuevas);
        }
        if (cmd.criteria() != null) {
            List<EvaluationCriteria> nuevos = new ArrayList<>();
            for (EvaluationCriteriaDto c : cmd.criteria()) {
                nuevos.add(EvaluationCriteria.create(c.id(), c.name(), c.description(), c.weight(), c.maxScore()));
            }
            exam.setCriteria(nuevos);
        }
    }

    private static List<RespuestaPregunta> mapAnswers(SubmitExamCommand command) {
        if (command.answers() == null) {
            return List.of();
        }
        return command.answers().stream().map(ExamenService::mapQuestionAnswer).toList();
    }

    private static RespuestaPregunta mapQuestionAnswer(QuestionAnswerDto a) {
        RespuestaPregunta r = new RespuestaPregunta();
        r.setPreguntaId(a.questionId());
        if (a.textAnswer() != null) {
            r.setRespuestaTexto(a.textAnswer());
        } else if (a.numericAnswer() != null) {
            r.setPuntaje(a.numericAnswer());
        } else if (a.booleanAnswer() != null) {
            r.setRespuestaTexto(Boolean.toString(a.booleanAnswer()));
        } else if (a.selectedOptionId() != null) {
            r.setRespuestaTexto(String.valueOf(a.selectedOptionId()));
        }
        return r;
    }
}
