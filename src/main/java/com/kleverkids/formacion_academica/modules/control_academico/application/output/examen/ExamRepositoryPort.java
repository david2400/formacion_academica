package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ExamRepository {
    
    Exam save(Exam exam);
    
    Optional<Exam> findById(UUID id);
    
    void deleteById(UUID id);
    
    Page<Exam> search(ExamSearchCriteria criteria, Pageable pageable);
    
    boolean existsById(UUID id);
}
