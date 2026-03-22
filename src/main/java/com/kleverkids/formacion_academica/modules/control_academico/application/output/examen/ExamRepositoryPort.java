package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExamRepositoryPort {
    
    Exam save(Exam exam);
    
    Optional<Exam> findById(Long id);
    
    void deleteById(Long id);
    
    Page<Exam> search(ExamSearchCriteria criteria, Pageable pageable);
    
    boolean existsById(Long id);
    
    // Métodos adicionales para compatibilidad con el servicio - implementaciones básicas
    default Exam guardar(Object request) {
        // Implementación por defecto - lanzar excepción para que el servicio lo maneje
        throw new UnsupportedOperationException("Método guardar no implementado en este adaptador");
    }
    
    default Object registrarCalificacion(Object request) {
        // Implementación por defecto - lanzar excepción para que el servicio lo maneje
        throw new UnsupportedOperationException("Método registrarCalificacion no implementado en este adaptador");
    }
}
