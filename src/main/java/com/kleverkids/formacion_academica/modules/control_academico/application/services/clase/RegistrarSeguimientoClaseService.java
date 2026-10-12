package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.RegistrarSeguimientoClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarSeguimientoClaseService implements RegistrarSeguimientoClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public Clase registrarSeguimiento(Long id, RegistrarSeguimientoClaseDto request) {
        return claseRepositoryPort.registrarSeguimiento(id, request);
    }
}
