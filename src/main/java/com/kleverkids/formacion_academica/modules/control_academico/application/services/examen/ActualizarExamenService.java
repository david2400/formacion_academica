package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.PreguntaExamen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.TimeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ActualizarExamenService implements ActualizarExamenUseCase {

    private final ExamenRepositoryPort examenRepository;

    @Override
    public ExamResponse actualizar(Long id, UpdateExamCommand command) {
        log.info("Actualizando examen ID: {}", id);
        Exam exam = examenRepository.findExamById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
        applyUpdate(exam, command);
        Exam saved = examenRepository.save(exam);
        return ExamResponse.fromDomain(saved);
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
}
