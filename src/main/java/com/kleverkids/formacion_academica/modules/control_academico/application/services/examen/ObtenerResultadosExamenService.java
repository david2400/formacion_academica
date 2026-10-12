package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ResultadoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObtenerResultadosExamenService implements ObtenerResultadosExamenUseCase {

    private final ResultadoExamenRepositoryPort resultRepository;

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
}
