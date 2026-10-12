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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CalificarExamenService implements CalificarExamenUseCase {

    private final ExamenRepositoryPort examenRepository;
    private final EnvioExamenRepositoryPort submissionRepository;
    private final ResultadoExamenRepositoryPort resultRepository;
    private final ServicioCalificacionExamen scoringService;

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
}
