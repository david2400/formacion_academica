package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ActualizarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarGradoService implements ActualizarGradoUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    @Override
    public Grado actualizar(ActualizarGradoDto request) {
        return gradoRepositoryPort.actualizar(request);
    }
}
