package com.kleverkids.formacion_academica.modules.control_academico.application.services.intento;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.IniciarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IniciarIntentoExamenService implements IniciarIntentoExamenUseCase {

    private final IntentoExamenRepositoryPort repositoryPort;

    @Override
    public IntentoExamenDto iniciar(IniciarIntentoExamenDto request) {
        return repositoryPort.iniciar(request);
    }
}
