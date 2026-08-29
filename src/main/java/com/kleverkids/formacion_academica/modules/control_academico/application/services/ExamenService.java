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
        EnvioExamen submission = submissionRepository.findById(command.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado: " + command.getSubmissionId()));
        if (!examenId.equals(submission.getExamenId())
                || !command.getStudentId().equals(submission.getEstudianteId())) {
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

        if (command.getGrades() != null) {
            for (GradeExamCommand.QuestionGradeDto grade : command.getGrades()) {
                submission.getRespuestas().stream()
                        .filter(r -> grade.getQuestionId().equals(r.getPreguntaId()))
                        .findFirst()
                        .ifPresent(r -> r.grade(grade.getScore(), grade.getFeedback()));
            }
        }

        ResultadoExamen resultado = scoringService.calculateResult(examenId, submission, exam.getTotalPoints());
        if (command.getGradedBy() != null) {
            resultado.setCalificadoPor(command.getGradedBy());
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
        if (cmd.getName() != null) {
            exam.setName(cmd.getName());
        }
        if (cmd.getCode() != null) {
            exam.setCode(cmd.getCode());
        }
        if (cmd.getSubject() != null) {
            exam.setSubject(cmd.getSubject());
        }
        if (cmd.getGradeLevel() != null) {
            exam.setGradeLevel(cmd.getGradeLevel());
        }
        if (cmd.getInstructions() != null) {
            exam.setInstructions(cmd.getInstructions());
        }
        if (cmd.getTimeConfig() != null) {
            TimeConfigDto tc = cmd.getTimeConfig();
            exam.setTimeConfig(TimeConfig.create(tc.getDuration(), tc.getScheduledDate(), tc.getStartTime(), tc.getEndTime()));
        }
        if (cmd.getQuestions() != null) {
            List<PreguntaExamen> nuevas = new ArrayList<>();
            for (ExamQuestionDto q : cmd.getQuestions()) {
                nuevas.add(new PreguntaExamen(q.getId(), q.getQuestionId(), q.getOrder(), q.getPoints(), q.isRequired()));
            }
            exam.setQuestions(nuevas);
        }
        if (cmd.getCriteria() != null) {
            List<EvaluationCriteria> nuevos = new ArrayList<>();
            for (EvaluationCriteriaDto c : cmd.getCriteria()) {
                nuevos.add(EvaluationCriteria.create(c.getId(), c.getName(), c.getDescription(), c.getWeight(), c.getMaxScore()));
            }
            exam.setCriteria(nuevos);
        }
    }

    private static List<RespuestaPregunta> mapAnswers(SubmitExamCommand command) {
        if (command.getAnswers() == null) {
            return List.of();
        }
        return command.getAnswers().stream().map(ExamenService::mapQuestionAnswer).toList();
    }

    private static RespuestaPregunta mapQuestionAnswer(QuestionAnswerDto a) {
        RespuestaPregunta r = new RespuestaPregunta();
        r.setPreguntaId(a.getQuestionId());
        if (a.getTextAnswer() != null) {
            r.setRespuestaTexto(a.getTextAnswer());
        } else if (a.getNumericAnswer() != null) {
            r.setPuntaje(a.getNumericAnswer());
        } else if (a.getBooleanAnswer() != null) {
            r.setRespuestaTexto(Boolean.toString(a.getBooleanAnswer()));
        } else if (a.getSelectedOptionId() != null) {
            r.setRespuestaTexto(String.valueOf(a.getSelectedOptionId()));
        }
        return r;
    }
}
