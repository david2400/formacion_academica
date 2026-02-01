package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchQuestionsUseCase {
    
    Page<QuestionResponse> search(QuestionSearchCriteria criteria, Pageable pageable);
}
