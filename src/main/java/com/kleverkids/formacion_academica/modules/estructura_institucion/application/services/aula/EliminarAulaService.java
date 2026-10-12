package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.EliminarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarAulaService implements EliminarAulaUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    @Override
    public void eliminar(Long aulaId) {
        validarExistencia(aulaId);
        aulaRepositoryPort.eliminar(aulaId);
    }

    private void validarExistencia(Long aulaId) {
        if (!aulaRepositoryPort.existePorId(aulaId)) {
            throw new IllegalArgumentException("El aula no existe");
        }
    }
}
