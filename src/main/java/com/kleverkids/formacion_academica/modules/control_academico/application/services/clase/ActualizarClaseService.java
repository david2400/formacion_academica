package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ActualizarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarClaseService implements ActualizarClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public Clase actualizar(ActualizarClaseDto request) {
        return claseRepositoryPort.actualizar(request);
    }
}
