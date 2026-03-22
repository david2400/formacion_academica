package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExamenJpaAdapter implements ExamenRepositoryPort {

    private final ExamenJpaRepository examenJpaRepository;

    @Override
    @Transactional
    public Examen save(Examen examen) {
        log.debug("Guardando examen: {}", examen.nombre());
        
        // Implementación mínima - retornar el mismo examen por ahora
        return examen;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Examen> findById(Long id) {
        log.debug("Buscando examen por ID: {}", id);
        
        // Implementación mínima - retornar Optional.empty() por ahora
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Eliminando examen por ID: {}", id);
        examenJpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Examen> search(ExamSearchCriteria criteria, Pageable pageable) {
        log.debug("Buscando exámenes con criterios: {}", criteria);
        
        // Implementación mínima - retornar página vacía por ahora
        return Page.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.debug("Verificando si existe examen con ID: {}", id);
        return examenJpaRepository.existsById(id);
    }
    
    // Métodos adicionales para compatibilidad con el servicio - implementaciones básicas
    @Override
    public Examen guardar(CrearExamenDto request) {
        // Implementación por defecto - lanzar excepción para que el servicio lo maneje
        throw new UnsupportedOperationException("Método guardar no implementado en este adaptador");
    }
    
    @Override
    public CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request) {
        // Implementación por defecto - lanzar excepción para que el servicio lo maneje
        throw new UnsupportedOperationException("Método registrarCalificacion no implementado en este adaptador");
    }
    
    // Métodos de compatibilidad con Exam (transición)
    @Override
    public Exam save(Exam exam) {
        log.debug("Guardando exam: {}", exam.getName());
        
        // Implementación mínima - retornar el mismo exam por ahora
        return exam;
    }
    
    @Override
    public Optional<Exam> findExamById(Long id) {
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }
    
    @Override
    public Page<Exam> searchExams(ExamSearchCriteria criteria, Pageable pageable) {
        // Implementación mínima por ahora - retornar página vacía
        return Page.empty();
    }
}
