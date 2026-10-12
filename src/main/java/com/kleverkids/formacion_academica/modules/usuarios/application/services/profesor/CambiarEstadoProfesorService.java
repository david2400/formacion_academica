package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.ConsultarProfesorUseCase;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CambiarEstadoProfesorService implements CambiarEstadoProfesorUseCase {

    private final ProfesorRepositoryPort repository;
    private final ProfesorSoporte profesorSoporte;
    private final ConsultarProfesorUseCase consultarProfesorUseCase;

    @Override
    @Transactional
    public ProfesorDto cambiarEstado(Long id, boolean activo) {
        Profesor profesor = profesorSoporte.existente(id);
        if (profesor.isActivo() != activo) {
            profesor.setActivo(activo);
            repository.guardar(profesor);
        }
        return consultarProfesorUseCase.consultar(id);
    }
}
