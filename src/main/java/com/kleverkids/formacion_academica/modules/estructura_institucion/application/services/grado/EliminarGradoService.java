package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.EliminarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarGradoService implements EliminarGradoUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    @Override
    public void eliminar(Long gradoId) {
        gradoRepositoryPort.obtenerPorId(gradoId);
        gradoRepositoryPort.eliminar(gradoId);
    }
}
