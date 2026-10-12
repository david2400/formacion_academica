package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.EnvioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IniciarExamenService implements IniciarExamenUseCase {

    private final ExamenRepositoryPort examenRepository;
    private final EnvioExamenRepositoryPort submissionRepository;

    @Override
    public ExamSubmissionResponse iniciar(Long examenId, Long estudianteId) {
        log.info("Iniciando examen ID: {} para estudiante: {}", examenId, estudianteId);
        examenRepository.findExamById(examenId).orElseThrow(() -> new ExamNotFoundException(examenId));
        EnvioExamen envio = submissionRepository.findByExamIdAndStudentId(examenId, estudianteId)
                .orElseGet(() -> submissionRepository.guardar(new EnvioExamen(null, examenId, estudianteId)));
        return ExamSubmissionResponse.fromDomain(envio);
    }
}
