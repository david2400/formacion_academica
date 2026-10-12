package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BuscarExamenesService implements BuscarExamenesUseCase {

    private final ExamenRepositoryPort examenRepository;

    @Override
    public Page<ExamResponse> buscar(ExamSearchCriteria criterios, Pageable pageable) {
        log.info("Buscando exámenes con criterios: {}", criterios);
        return examenRepository.searchExams(criterios, pageable).map(ExamResponse::fromDomain);
    }
}
