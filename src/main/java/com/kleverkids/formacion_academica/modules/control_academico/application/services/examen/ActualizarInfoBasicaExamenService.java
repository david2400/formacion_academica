package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ActualizarInfoBasicaExamenService implements ActualizarInfoBasicaExamenUseCase {

    private final ExamenRepositoryPort examenRepository;

    @Override
    public Examen actualizarInfoBasica(Long id, ActualizarInfoBasicaExamenDto request) {
        log.info("Actualizando información básica del examen ID: {}", id);
        return examenRepository.actualizarInfoBasica(id, request);
    }
}
