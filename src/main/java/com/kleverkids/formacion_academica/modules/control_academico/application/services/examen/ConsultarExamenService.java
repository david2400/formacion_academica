package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConsultarExamenService implements ConsultarExamenUseCase {

    private final ExamenRepositoryPort examenRepository;

    @Override
    public ExamResponse consultarPorId(Long id) {
        log.info("Consultando examen por ID: {}", id);
        // findExamById (agregado Exam) nunca se conectó a la base de datos y
        // siempre devuelve empty -mismo motivo que buscar() de arriba-, así
        // que esto se arma desde el camino legado (Examen), igual que
        // searchExams en ExamenJpaAdapter. code queda "" a propósito: el
        // resto del contrato de ExamResponse asume que nunca es null.
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
        Exam exam = new Exam();
        exam.setId(examen.getId());
        exam.setName(examen.getNombre());
        exam.setCode("");
        return ExamResponse.fromDomain(exam);
    }
}
