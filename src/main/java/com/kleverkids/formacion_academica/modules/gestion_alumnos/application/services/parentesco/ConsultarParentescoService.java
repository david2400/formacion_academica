package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ConsultarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarParentescoService implements ConsultarParentescoUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;

    @Override
    public Optional<Parentesco> consultarPorId(Long id) {
        return parentescoRepositoryPort.obtenerPorId(id);
    }
}
