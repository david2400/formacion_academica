package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CriterioBusquedaPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PreguntaRepository {
    
    Pregunta save(Pregunta question);
    
    Optional<Pregunta> findById(Long id);
    
    void deleteById(Long id);
    
    Page<Pregunta> search(CriterioBusquedaPregunta criteria, Pageable pageable);
    
    boolean existsById(Long id);
}
