package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.EnvioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;
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
public class EnvioExamenJpaAdapter implements EnvioExamenRepositoryPort {

    @Override
    @Transactional
    public EnvioExamen save(EnvioExamen submission) {
        log.debug("Guardando submission: {}", submission.getId());
        // Implementación mínima - retornar el mismo submission
        return submission;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnvioExamen> findById(Long id) {
        log.debug("Buscando submission por ID: {}", id);
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvioExamen> findByExamId(Long examId) {
        log.debug("Buscando submissions para examen {}", examId);
        // Implementación mínima - retornar lista vacía por ahora
        return List.of();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<EnvioExamen> findByExamIdAndStudentId(Long examId, Long studentId) {
        log.debug("Buscando submission para examen {} y estudiante {}", examId, studentId);
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }
    
    // Métodos de compatibilidad adicionales
    @Override
    public EnvioExamen guardar(EnvioExamen envio) {
        return save(envio);
    }
    
    @Override
    public Optional<EnvioExamen> findExamById(Long id) {
        return findById(id);
    }
}
