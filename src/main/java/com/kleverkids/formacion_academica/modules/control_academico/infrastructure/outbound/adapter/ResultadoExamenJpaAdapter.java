package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ResultadoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResultadoExamenJpaAdapter implements ResultadoExamenRepositoryPort {

    @Override
    @Transactional
    public ResultadoExamen save(ResultadoExamen result) {
        log.debug("Guardando resultado: {}", result.getId());
        // Implementación mínima - retornar el mismo resultado
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultadoExamen> findById(Long id) {
        log.debug("Buscando resultado por ID: {}", id);
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultadoExamen> findByExamIdAndStudentId(Long examId, Long studentId) {
        log.debug("Buscando resultado para examen {} y estudiante {}", examId, studentId);
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultadoExamen> findByExamId(Long examId) {
        log.debug("Buscando resultados para examen {}", examId);
        // Implementación mínima - retornar lista vacía por ahora
        return List.of();
    }
}
