package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearClaseService implements CrearClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public Clase crearClase(CrearClaseDto request) {
        return claseRepositoryPort.guardar(request);
    }
}
