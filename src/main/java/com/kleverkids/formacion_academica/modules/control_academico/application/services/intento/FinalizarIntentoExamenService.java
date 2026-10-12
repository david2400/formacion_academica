package com.kleverkids.formacion_academica.modules.control_academico.application.services.intento;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.FinalizarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalizarIntentoExamenService implements FinalizarIntentoExamenUseCase {

    private final IntentoExamenRepositoryPort repositoryPort;

    @Override
    public IntentoExamenDto finalizar(FinalizarIntentoExamenDto request) {
        return repositoryPort.finalizar(request);
    }
}
