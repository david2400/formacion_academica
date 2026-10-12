package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.EnvioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ResultadoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EnviarExamenService implements EnviarExamenUseCase {

    private final ExamenRepositoryPort examenRepository;
    private final EnvioExamenRepositoryPort submissionRepository;
    private final ResultadoExamenRepositoryPort resultRepository;
    private final ServicioCalificacionExamen scoringService;

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

    private static List<RespuestaPregunta> mapAnswers(SubmitExamCommand command) {
        if (command.getAnswers() == null) {
            return List.of();
        }
        return command.getAnswers().stream().map(EnviarExamenService::mapQuestionAnswer).toList();
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
