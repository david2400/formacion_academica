package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.EliminarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarClaseService implements EliminarClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public void eliminar(Long id) {
        claseRepositoryPort.eliminar(id);
    }
}
