package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EliminarProfesorService implements EliminarProfesorUseCase {

    private final ProfesorRepositoryPort repository;
    private final ProfesorSoporte profesorSoporte;

    @Override
    @Transactional
    public void eliminar(Long id) {
        profesorSoporte.existente(id);
        repository.eliminar(id);
    }
}
