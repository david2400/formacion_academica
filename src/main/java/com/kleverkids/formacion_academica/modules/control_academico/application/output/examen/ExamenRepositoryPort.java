package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ActualizarInfoBasicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExamenRepositoryPort {

    // Métodos en español
    Examen guardar(CrearExamenDto request);
    CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request);
    
    // Métodos compatibles con el servicio existente
    Examen save(Examen examen);
    Optional<Examen> findById(Long id);
    void deleteById(Long id);
    Page<Examen> search(ExamSearchCriteria criteria, Pageable pageable);
    boolean existsById(Long id);

    // Actualización parcial de datos básicos (nombre/descripción) por el camino legado
    Examen actualizarInfoBasica(Long id, ActualizarInfoBasicaExamenDto dto);
    
    // Sobrecargas para compatibilidad con Exam (transición)
    Exam save(Exam exam);
    Optional<Exam> findExamById(Long id);
    Page<Exam> searchExams(ExamSearchCriteria criteria, Pageable pageable);
}
