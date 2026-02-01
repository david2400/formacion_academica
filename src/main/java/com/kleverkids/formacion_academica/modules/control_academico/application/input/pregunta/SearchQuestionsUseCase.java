package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionResponse;
import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchQuestionsUseCase {
    
    Page<QuestionResponse> search(QuestionSearchCriteria criteria, Pageable pageable);
}
