package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {
    
    Question save(Question question);
    
    Optional<Question> findById(UUID id);
    
    void deleteById(UUID id);
    
    Page<Question> search(QuestionSearchCriteria criteria, Pageable pageable);
    
    boolean existsById(UUID id);
}
