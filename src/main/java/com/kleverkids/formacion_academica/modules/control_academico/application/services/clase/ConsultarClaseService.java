package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ConsultarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarClaseService implements ConsultarClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public Optional<Clase> consultarPorId(Long id) {
        return claseRepositoryPort.obtenerPorId(id);
    }
}
