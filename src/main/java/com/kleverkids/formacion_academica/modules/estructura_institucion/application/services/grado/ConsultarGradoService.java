package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ConsultarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarGradoService implements ConsultarGradoUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    @Override
    public Grado consultarPorId(Long gradoId) {
        return gradoRepositoryPort.obtenerPorId(gradoId);
    }
}
