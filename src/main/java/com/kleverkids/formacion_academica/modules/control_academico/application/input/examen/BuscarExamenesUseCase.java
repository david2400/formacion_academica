package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuscarExamenesUseCase {
    
    Page<ExamResponse> buscar(ExamSearchCriteria criterios, Pageable pageable);
}
