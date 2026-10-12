package com.kleverkids.formacion_academica.modules.control_academico.application.services.intento;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.RegistrarRespuestaIntentoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarRespuestaIntentoService implements RegistrarRespuestaIntentoUseCase {

    private final IntentoExamenRepositoryPort repositoryPort;

    @Override
    public RespuestaIntentoDto registrar(RegistrarRespuestaIntentoDto request) {
        return repositoryPort.registrarRespuesta(request);
    }
}
